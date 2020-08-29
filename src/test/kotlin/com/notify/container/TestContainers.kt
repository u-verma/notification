package com.notify.container

import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.wait.strategy.Wait
import java.time.Duration

private val POSTGRES_PORT = 5432

class CustomPostgraceSQLContainer(imageName: String) : PostgreSQLContainer<CustomPostgraceSQLContainer?>(imageName)

val postgres: CustomPostgraceSQLContainer =
        CustomPostgraceSQLContainer("postgres:latest")
                .withDatabaseName("notification")
                ?.withUsername("test")
                ?.withPassword("password")
                ?.withExposedPorts(POSTGRES_PORT)
                ?.waitingFor(
                        Wait.forLogMessage(
                                ".*database system is ready to accept connections\n", 1)
                                .withStartupTimeout(Duration.ofSeconds(60)))!!

fun startPostgres() = postgres.start()
fun getPostgresMappedPort(): Int = postgres.getMappedPort(POSTGRES_PORT)
fun getPostgresUrl(): String =  postgres.jdbcUrl
fun getUsername() = postgres.username
fun getPassword() = postgres.password
fun getDatabaseName() = postgres.databaseName
