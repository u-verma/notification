package com.notify.client.email.service

import com.notify.client.email.domain.EmailMetaData
import com.notify.client.email.domain.UserPojo
import com.notify.client.email.repository.UserPreferenceRepository
import com.notify.client.reddit.client.RedditClientService
import com.notify.client.reddit.domain.RedditPost
import com.notify.common.domain.ChannelId
import com.sendgrid.helpers.mail.objects.Content
import org.springframework.stereotype.Service

@Service
class EmailServiceImpl(
        private val userPreferenceRepository: UserPreferenceRepository,
        private val redditClientService: RedditClientService,
        private val emailClient: EmailClient) : EmailService {
    override fun execute(userList: List<String>) {
        getUserChannelPreferences(userList);
    }

    private fun getUserChannelPreferences(userList: List<String>) {
        val userChanelMapping = userPreferenceRepository.fetchUserChannelPreference(userList)

        val channelIdSet = hashSetOf<String>()
        userChanelMapping.values.flatMap { values ->
            values.map { channelId -> channelIdSet.add(channelId.value.toString()) }
        }
        val redditPost = callRedditClient(channelIdSet)
        val userEmailMetaDataList = createEmailData(userChanelMapping, redditPost)
        //TODO Implement Executor Service to send mail
        userEmailMetaDataList.forEach { emailClient.sendHTMLEmail(it) }
    }

    private fun callRedditClient(channelIdSet: HashSet<String>): Map<ChannelId, List<RedditPost>> {
        val channelMapping = userPreferenceRepository.fetchSubscribedChannel(channelIdSet)
        return redditClientService.fetchSubRedditPost(channelMapping)
    }

    private fun createEmailData(userChanelMapping: HashMap<UserPojo, MutableList<ChannelId>>,
                                redditPostChannelMapping: Map<ChannelId, List<RedditPost>>): MutableList<EmailMetaData> {
        val emailMetaDetaList = mutableListOf<EmailMetaData>()
        userChanelMapping.mapValues { entry: Map.Entry<UserPojo, MutableList<ChannelId>> ->
            val from = "er.umeshverma@gmail.com"
            val to = entry.key.emailId.value
            val subject = "Good Morning News"
            val contentlist = mutableListOf<String>()

            entry.value.forEach {
                redditPostChannelMapping[it]
                        ?.forEach {
                            contentlist.add(
                                    """
                                    |<!DOCTYPE html>
                                    |<html>
                                    |<body>
                                    |<h1>${it.postTitle}</h1>
                                    |<p> Check the full Post at the below URL</p>
                                    |<a href="${it.postLink}"> Click the URL for more Information</a>
                                    |</body>
                                    |</html> """.trimMargin("|") + "\n\n\n")
                        }
            }
            val content = Content("text/html", contentlist
                    .joinToString { it })
            emailMetaDetaList.add(EmailMetaData(from, to, subject, content))
        }
        return emailMetaDetaList
    }
}