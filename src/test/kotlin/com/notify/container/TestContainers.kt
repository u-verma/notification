package com.notify.container

import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.wait.strategy.Wait
import java.time.Duration

private val POSTGRES_PORT = 5432

val postgres: CustomPostgraceSQLContainer =
        CustomPostgraceSQLContainer("postgres:12.2")
                .withDatabaseName("notification")
                ?.withUsername("test")
                ?.withPassword("password")
                ?.withExposedPorts(POSTGRES_PORT)
                ?.waitingFor(
                        Wait.forLogMessage(
                                ".*database system is ready to accept connections\n", 1)
                                .withStartupTimeout(Duration.ofSeconds(30)))!!


class CustomPostgraceSQLContainer(private val imageName: String) : PostgreSQLContainer<CustomPostgraceSQLContainer?>()

fun startPostgres() = postgres.start()

fun getPostgresMappedPort() = postgres.getMappedPort(POSTGRES_PORT)
