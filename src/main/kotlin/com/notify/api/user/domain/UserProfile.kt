package com.notify.api.user.domain

import com.notify.common.NotificationStatus
import com.notify.common.domain.EmailId
import com.notify.common.domain.UserId
import java.time.LocalDateTime
import java.time.LocalTime

data class UserProfile(
        val userId: UserId,
        val fullName: String,
        val emailId: EmailId,
        val countryCode: String,
        val notificationStatus: NotificationStatus,
        val notificationTsUtc: LocalDateTime,
        val lastEmailSentTsUtc: LocalDateTime
)