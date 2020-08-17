package com.notify.api.user.domain

data class UserDetailsResponse(
        val userId: String,
        val emailId: String,
        val notificationStatus: String
)