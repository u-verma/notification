package com.notify.client.reddit.domain

import com.notify.common.domain.ChannelId

data class RedditData(
        val channelId: ChannelId,
        val redditPostList: List<RedditPost>
)
