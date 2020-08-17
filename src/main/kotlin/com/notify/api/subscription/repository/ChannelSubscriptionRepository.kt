package com.notify.api.subscription.repository

import com.notify.api.subscription.domain.SubscribedRedditChannel
import com.notify.databases.model.Tables
import com.notify.databases.model.tables.records.SubscribedRedditChannelRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime
import java.util.Optional

@Repository
class ChannelSubscriptionRepository(private val dslContext: DSLContext) {
    fun save(subscribedRedditChannel: SubscribedRedditChannel): Boolean {
        return 1 == dslContext
                .insertInto(Tables.SUBSCRIBED_REDDIT_CHANNEL)
                .set((subscribedRedditChannel.toSubscribedRedditChannelRecord()))
                .onConflict(Tables.SUBSCRIBED_REDDIT_CHANNEL.REDDIT_CHANNEL)
                .doUpdate()
                .set(Tables.SUBSCRIBED_REDDIT_CHANNEL.REDDIT_CHANNEL,
                        subscribedRedditChannel.redditChannel.value)
                .execute()
    }

    fun fetchChannelIfExist(redditChannel: String): Optional<SubscribedRedditChannelRecord> {
        return dslContext.selectFrom(Tables.SUBSCRIBED_REDDIT_CHANNEL)
                .where(Tables.SUBSCRIBED_REDDIT_CHANNEL.REDDIT_CHANNEL.eq(redditChannel))
                .fetchOptional()
    }
}

private fun SubscribedRedditChannel.toSubscribedRedditChannelRecord() =
        SubscribedRedditChannelRecord(
                this.channelId.value.toString(),
                this.redditChannel.value,
                OffsetDateTime.now(),
                OffsetDateTime.now()
        )