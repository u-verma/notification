package com.notify.client.reddit.client

import com.notify.client.reddit.domain.RedditData
import com.notify.client.reddit.domain.RedditPost
import com.notify.client.reddit.task.SubRedditContentFetchTask
import com.notify.common.domain.ChannelId
import com.notify.common.domain.RedditChannel
import org.apache.http.impl.client.CloseableHttpClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.ArrayList
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorCompletionService
import java.util.concurrent.Future

@Service
class RedditClient(
        private val completionService: ExecutorCompletionService<RedditData>,
        private val httpClient: CloseableHttpClient
) : RedditClientService {
    private val logger = LoggerFactory.getLogger(RedditClient::class.java)

    override fun fetchSubRedditPost(channelMapping: Map<ChannelId, RedditChannel>): Map<ChannelId, List<RedditPost>> {
        val submittedTask = ArrayList<Future<RedditData>>()
        val redditPostMapping = HashMap<ChannelId, List<RedditPost>>()
        channelMapping.forEach { (redditId, redditEndPointUrl) ->
            val task = SubRedditContentFetchTask(httpClient, redditId, redditEndPointUrl)
            submittedTask.add(completionService.submit(task))
        }
        try {
            submittedTask.forEach {
                val redditData = it.get()
                redditPostMapping[redditData.channelId] = redditData.redditPostList
            }
        } catch (ex: InterruptedException) {
            logger.error("Error Executor Thread Interrupted ${ex.message}")
            Thread.currentThread().interrupt()
        } catch (ex: ExecutionException) {
            logger.error("Error During fetching Reddit data  ${ex.message}")
        }
        return redditPostMapping;
    }
}