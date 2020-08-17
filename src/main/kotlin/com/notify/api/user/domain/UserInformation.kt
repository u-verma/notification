package com.notify.api.user.domain

import com.notify.common.NotificationStatus
import com.notify.common.domain.EmailId

data class UserInformation(
        val fullName: String,
        val emailId: String,
        val countryCode: String,
        val notificationStatus: NotificationStatus
)