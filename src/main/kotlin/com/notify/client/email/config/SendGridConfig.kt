package com.notify.client.email.config

import com.sendgrid.SendGrid
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SendGridConfig {
    @Bean
    fun sendGridClient(@Value("\${spring.sendgrid.api.key}") sendGridAPIKey: String): SendGrid {
        return SendGrid(sendGridAPIKey);
    }
}

