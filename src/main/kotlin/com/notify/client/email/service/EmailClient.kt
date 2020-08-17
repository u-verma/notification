package com.notify.client.email.service

import com.notify.client.email.domain.EmailMetaData
import com.sendgrid.Response

interface EmailClient {
    fun sendHTMLEmail(emailMetaData: EmailMetaData) : Response?
}