package com.notify.client.email.repository

import com.notify.client.email.domain.UserPojo
import com.notify.common.domain.ChannelId
import com.notify.common.domain.EmailId
import com.notify.common.domain.RedditChannel
import com.notify.common.domain.UserId
import com.notify.databases.model.Tables.SUBSCRIBED_REDDIT_CHANNEL
import com.notify.databases.model.Tables.USER_PROFILE
import com.notify.databases.model.Tables.USER_REDDIT_CHANNEL_MAPPING
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class UserPreferenceRepository(private val dslContext: DSLContext) {

    fun fetchUserChannelPreference(userList: List<String>): HashMap<UserPojo, MutableList<ChannelId>> {
        val userPrefMap = HashMap<UserPojo, MutableList<ChannelId>>()
        dslContext
                .select(USER_PROFILE.USER_ID, USER_PROFILE.EMAIL_ID)
                .select(USER_REDDIT_CHANNEL_MAPPING.CHANNEL_ID)
                .from(USER_PROFILE)
                .innerJoin(USER_REDDIT_CHANNEL_MAPPING)
                .on(USER_REDDIT_CHANNEL_MAPPING.USER_ID.eq(USER_PROFILE.USER_ID))
                .fetch().forEach { record ->
                    val userPojo = UserPojo(
                            UserId(UUID.fromString(record.getValue(USER_PROFILE.USER_ID))),
                            EmailId(record.getValue(USER_PROFILE.EMAIL_ID))
                    )
                    if (!userPrefMap.containsKey(userPojo)) {
                        userPrefMap[userPojo] = mutableListOf(ChannelId(
                                UUID.fromString(record.getValue(USER_REDDIT_CHANNEL_MAPPING.CHANNEL_ID))))
                    } else {
                        val channelList = userPrefMap[userPojo]
                        channelList?.add(ChannelId(
                                UUID.fromString(record.getValue(USER_REDDIT_CHANNEL_MAPPING.CHANNEL_ID))))
                        userPrefMap[userPojo] = channelList!!
                    }
                }
        return userPrefMap
    }

    fun fetchSubscribedChannel(channelIdSet: HashSet<String>): MutableMap<ChannelId, RedditChannel> {
        val channelMapping = mutableMapOf<ChannelId, RedditChannel>()
        dslContext
                .select(SUBSCRIBED_REDDIT_CHANNEL.CHANNEL_ID,
                        SUBSCRIBED_REDDIT_CHANNEL.REDDIT_CHANNEL)
                .from(SUBSCRIBED_REDDIT_CHANNEL)
                .where(SUBSCRIBED_REDDIT_CHANNEL.CHANNEL_ID.`in`(channelIdSet))
                .fetch().forEach {
                    channelMapping[ChannelId((UUID.fromString(it.getValue(SUBSCRIBED_REDDIT_CHANNEL.CHANNEL_ID))))] =
                            RedditChannel(it.getValue(SUBSCRIBED_REDDIT_CHANNEL.REDDIT_CHANNEL))
                }
        return channelMapping
    }
}