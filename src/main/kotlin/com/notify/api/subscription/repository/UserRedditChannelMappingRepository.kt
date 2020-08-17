package com.notify.api.subscription.repository

import com.notify.common.domain.UserRedditChannelMapping
import com.notify.databases.model.Tables.USER_REDDIT_CHANNEL_MAPPING
import com.notify.databases.model.tables.records.UserRedditChannelMappingRecord
import org.jooq.DSLContext
import org.jooq.Record
import org.springframework.stereotype.Repository

@Repository
class UserRedditChannelMappingRepository(private val dslContext: DSLContext) {
    fun save(userRedditChannelMapping: UserRedditChannelMapping): Boolean {
        return 1 == dslContext
                .insertInto(USER_REDDIT_CHANNEL_MAPPING)
                .set(userRedditChannelMapping.toUserRedditChannelMappingRecord())
                .execute()
    }

    fun deleteOldMapping(userId: String): Boolean {
        return 1 == dslContext.deleteFrom(USER_REDDIT_CHANNEL_MAPPING)
                .where(USER_REDDIT_CHANNEL_MAPPING.USER_ID.eq(userId))
                .execute()
    }
}

private fun UserRedditChannelMapping.toUserRedditChannelMappingRecord() =
        UserRedditChannelMappingRecord(
                this.userId.value.toString(),
                this.channelId.value.toString()
        )