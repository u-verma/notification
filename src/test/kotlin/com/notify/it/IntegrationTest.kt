package com.notify.it

import com.notify.NotificationApplication
import com.notify.container.getPostgresMappedPort
import com.notify.container.startPostgres
import io.restassured.RestAssured
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = [NotificationApplication::class],
        properties = ["spring.main.allow-bean-definition-overriding=true"]
)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class IntegrationTest {
    private val log = LoggerFactory.getLogger(IntegrationTest::class.java)

    companion object {
        init {
            startPostgres()
            val postgresMappedPort = getPostgresMappedPort()
            System.setProperty("postgres.post", postgresMappedPort.toString())
        }
    }

    @LocalServerPort
    var serverPort = 0

    @Autowired
    @BeforeAll
    fun initializedRestAssured() {
        RestAssured.port = serverPort
    }
}