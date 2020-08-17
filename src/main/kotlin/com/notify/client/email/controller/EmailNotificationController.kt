package com.notify.client.email.controller

import com.notify.api.exception.UserDoesNotExistException
import com.notify.client.email.service.EmailService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/email")
class EmailNotificationController(private val emailService: EmailService) {
    private val logger = LoggerFactory.getLogger(EmailNotificationController::class.java)

    @GetMapping("/send")
    fun sendEmailToUsers(@RequestBody userId: List<String>) {
        try {
            emailService.execute(userId)
        } catch (ex: UserDoesNotExistException) {
            logger.error(ex.message)
            throw ex
        }
    }
}