package com.notify.client.email.domain

import com.notify.common.domain.EmailId
import com.notify.common.domain.UserId

data class UserPojo(
        val userId: UserId,
        val emailId: EmailId
)