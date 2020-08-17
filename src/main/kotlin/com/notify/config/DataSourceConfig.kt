package com.notify.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.TransactionProvider
import org.jooq.impl.DataSourceConnectionProvider
import org.jooq.impl.DefaultConfiguration
import org.jooq.impl.DefaultDSLContext
import org.jooq.impl.ThreadLocalTransactionProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class DataSourceConfig {

    @Bean
    fun dataSource(
            @Value("\${spring.datasource.url}") url: String,
            @Value("\${spring.datasource.username}") username: String,
            @Value("\${spring.datasource.password}") password: String,
            @Value("\${spring.datasource.hikari.connection-timeout}") connectionTimeout: String,
            @Value("\${spring.datasource.hikari.maximum-pool-size}") maxPoolSize: Int
    ): DataSource {
        val hikariConfig = HikariConfig()
        hikariConfig.jdbcUrl = url
        hikariConfig.username = username
        hikariConfig.password = password
        hikariConfig.connectionTimeout = java.lang.Long.valueOf(connectionTimeout)
        hikariConfig.isAutoCommit = true
        hikariConfig.transactionIsolation = "TRANSACTION_READ_COMMITTED"
        hikariConfig.poolName = "notification-db"
        hikariConfig.maximumPoolSize = maxPoolSize
        return HikariDataSource(hikariConfig)
    }

    @Bean
    fun jooqTransactionProvider(dataSource: DataSource): TransactionProvider {
        return ThreadLocalTransactionProvider(DataSourceConnectionProvider(dataSource))
    }

    @Bean
    fun dslContext(dataSource: DataSource, transactionProvider: TransactionProvider): DSLContext {
        val config = DefaultConfiguration()
        config.setDataSource(dataSource)
        config.setSQLDialect(SQLDialect.POSTGRES)
        config.setTransactionProvider(transactionProvider)
        return DefaultDSLContext(config)
    }
}