package com.notify.client.email.domain

import com.sendgrid.helpers.mail.objects.Content

data class EmailMetaData(
        val from: String,
        val to: String,
        val subject: String,
        val content: Content
)