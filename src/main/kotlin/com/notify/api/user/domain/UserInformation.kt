package com.notify.api.user.domain

import com.notify.common.NotificationStatus

data class UserInformation(
        val fullName: String,
        val emailId: String,
        val countryCode: String,
        val notificationStatus: NotificationStatus
)