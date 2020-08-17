package com.notify.client.reddit.client

import com.notify.client.reddit.domain.RedditPost
import com.notify.common.domain.ChannelId
import com.notify.common.domain.RedditChannel

interface RedditClientService {
    fun fetchSubRedditPost(channelUrlMap: Map<ChannelId, RedditChannel>): Map<ChannelId, List<RedditPost>>
}