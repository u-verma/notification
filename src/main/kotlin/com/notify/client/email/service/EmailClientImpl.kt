package com.notify.client.email.service

import com.notify.client.email.domain.EmailMetaData
import com.sendgrid.Method
import com.sendgrid.Request
import com.sendgrid.Response
import com.sendgrid.SendGrid
import com.sendgrid.helpers.mail.Mail
import com.sendgrid.helpers.mail.objects.Email
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.IOException

@Service
class EmailClientImpl(private val sendGridClient: SendGrid) : EmailClient {

    private val logger = LoggerFactory.getLogger(EmailClientImpl::class.java)

    override fun sendHTMLEmail(emailMetaData: EmailMetaData) = sendEmail(emailMetaData)

    private fun sendEmail(emailMetaData: EmailMetaData): Response? {
        return try {
            sendGridClient.api(emailMetaData.buildMail().buildRequest())
        } catch (ex: IOException) {
            logger.error("Error occurred During Sending the Email for the User : ${emailMetaData.to}")
            throw ex
        }
    }

    private fun Mail.buildRequest(): Request {
        val request = Request()
        request.baseUri = "https://api.sendgrid.com/v3/"
        request.method = Method.POST
        request.endpoint = "mail/send"
        request.body = this.build()
        return request
    }

    private fun EmailMetaData.buildMail(): Mail =
            Mail(Email(this.from), this.subject, Email(this.to), this.content)
}