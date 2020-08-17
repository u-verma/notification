package com.notify.client.reddit.task

import com.notify.client.reddit.domain.RedditData
import com.notify.client.reddit.domain.RedditPost
import com.notify.common.domain.ChannelId
import com.notify.common.domain.RedditChannel
import org.apache.http.client.ClientProtocolException
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.CloseableHttpClient
import org.json.JSONObject
import org.json.JSONTokener
import org.slf4j.LoggerFactory
import java.io.IOException
import java.io.InputStream
import java.util.concurrent.Callable

class SubRedditContentFetchTask(
        private val httpClient: CloseableHttpClient,
        private val channelId: ChannelId,
        private val redditChannel: RedditChannel
) : Callable<RedditData> {

    private val logger = LoggerFactory.getLogger(SubRedditContentFetchTask::class.java)

    override fun call(): RedditData {
        return executeHttpGetRequest()
    }

    private fun executeHttpGetRequest(): RedditData {
        var redditData: RedditData? = null
        val getRequest = HttpGet(redditChannel.value.composeUrl())
        getRequest.setHeader("User-Agent", "PostmanRuntime/7.26.3")
        try {
            httpClient.execute(getRequest).use {
                redditData = parseHttpResponse(it.entity.content)
            }
        } catch (ex: IOException) {
            logger.error("Error occurred while fetching data from endpoint ${redditChannel.value} ${ex.message}")
        } catch (ex: ClientProtocolException) {
            logger.error("Error occurred while fetching data from endpoint ${redditChannel.value} ${ex.message}")
        } finally {
            getRequest.releaseConnection()
        }
        return redditData!!
    }

    private fun parseHttpResponse(inputStream: InputStream): RedditData {
        val redditPostList = ArrayList<RedditPost>()
        val jsonArray = JSONObject(JSONTokener(inputStream))
                .getJSONObject("data")
                .getJSONArray("children")

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i).getJSONObject("data")
            redditPostList.add(RedditPost(
                    jsonObject.get("title") as String,
                    jsonObject.get("url") as String
            ))
        }
        return RedditData(channelId, redditPostList)
    }

    private fun String.composeUrl() = "https://www.reddit.com/r/$this/top.json?limit=3&t=day"
}