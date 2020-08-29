package com.notify.it

import com.notify.NotificationApplicationTest
import com.notify.container.getPassword
import com.notify.container.getPostgresMappedPort
import com.notify.container.getPostgresUrl
import com.notify.container.getUsername
import com.notify.container.startPostgres
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(initializers = [IntegrationTest.Initializer::class])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
abstract class IntegrationTest {

    @Test
    fun contextLoads() {
    }

    class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        private val logger = LoggerFactory.getLogger(Initializer::class.java)

        override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
            startPostgres()
            val postgresMappedPort: Int = getPostgresMappedPort()
            System.setProperty("postgres.post", postgresMappedPort.toString())

            configurableApplicationContext.environment.systemProperties["spring.datasource.url"] = getPostgresUrl()
            configurableApplicationContext.environment.systemProperties["spring.datasource.username"] = getUsername();
            configurableApplicationContext.environment.systemProperties["spring.datasource.password"] = getPassword();
            configurableApplicationContext.environment.systemProperties["spring.datasource.hikari.connection-timeout"] = 1000
            configurableApplicationContext.environment.systemProperties["spring.datasource.hikari.maximum-pool-size"] = 10
            configurableApplicationContext.environment.systemProperties["spring.sendgrid.api.key"] = "SG-testApi-key"
            logger.info("Postgres started on port: $postgresMappedPort")
        }

    }


}
