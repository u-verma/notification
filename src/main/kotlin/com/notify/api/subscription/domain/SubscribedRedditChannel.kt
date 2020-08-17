package com.notify.api.subscription.domain

import com.notify.common.domain.ChannelId
import com.notify.common.domain.RedditChannel

class SubscribedRedditChannel(
        val channelId: ChannelId,
        val redditChannel: RedditChannel
)