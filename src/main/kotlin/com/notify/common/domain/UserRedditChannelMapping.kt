package com.notify.common.domain

import com.notify.common.domain.ChannelId
import com.notify.common.domain.UserId

data class UserRedditChannelMapping(
        val userId: UserId,
        val channelId: ChannelId
)