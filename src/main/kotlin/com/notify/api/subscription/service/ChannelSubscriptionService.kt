package com.notify.api.subscription.service

import com.notify.api.subscription.domain.RedditChannelInfo
import com.notify.api.subscription.domain.SubscribedRedditChannel
import com.notify.api.subscription.repository.ChannelSubscriptionRepository
import com.notify.api.subscription.repository.UserRedditChannelMappingRepository
import com.notify.common.domain.ChannelId
import com.notify.common.domain.RedditChannel
import com.notify.common.domain.UserId
import com.notify.common.domain.UserRedditChannelMapping
import com.notify.databases.model.tables.records.SubscribedRedditChannelRecord
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class ChannelSubscriptionService(
        private val channelSubscriptionRepository: ChannelSubscriptionRepository,
        private val userRedditChannelMappingRepository: UserRedditChannelMappingRepository
) {
    @Transactional
    fun addChannelSubscriptionForUser(redditChannelInfo: RedditChannelInfo) {
        channelSubscriptionRepository
                .fetchChannelIfExist(redditChannelInfo.redditChannel)
                .map { record: SubscribedRedditChannelRecord ->
                    val subscribedRedditChannel = record.toSubscribedRedditChannel()
                    userRedditChannelMappingRepository.save(UserRedditChannelMapping(
                            UserId(UUID.fromString(redditChannelInfo.userId)),
                            subscribedRedditChannel.channelId
                    ))
                }.orElseGet {
                    val subscribedRedditChannel = redditChannelInfo.toSubscribedRedditChannel()
                    channelSubscriptionRepository.save(subscribedRedditChannel)
                    userRedditChannelMappingRepository.save(UserRedditChannelMapping(
                            UserId(UUID.fromString(redditChannelInfo.userId)),
                            subscribedRedditChannel.channelId
                    ))
                }
    }

    @Transactional
    fun updateChannelSubscriptionForUser(redditChannelInfo: RedditChannelInfo) {
        userRedditChannelMappingRepository.deleteOldMapping(redditChannelInfo.userId)
        addChannelSubscriptionForUser(
                RedditChannelInfo(
                        redditChannelInfo.userId,
                        redditChannelInfo.redditChannel
                )
        )
    }
}

private fun RedditChannelInfo.toSubscribedRedditChannel() =
        SubscribedRedditChannel(
                ChannelId(UUID.randomUUID()),
                RedditChannel(this.redditChannel)
        )

private fun SubscribedRedditChannelRecord.toSubscribedRedditChannel() =
        SubscribedRedditChannel(
                ChannelId(UUID.fromString(this.channelId)),
                RedditChannel(this.redditChannel)
        )

