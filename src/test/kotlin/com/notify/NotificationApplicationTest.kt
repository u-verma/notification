package com.notify

import com.notify.it.IntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(
        webEnvironment = RANDOM_PORT,
        classes = [NotificationApplication::class],
        properties = ["spring.main.allow-bean-definition-overriding=true"]
)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class NotificationApplicationTest : IntegrationTest(){

    @Test
    fun migrate() {
        // migration starts automatically,
        // since Spring Boot runs the Flyway scripts on startup
    }

}