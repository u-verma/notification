package com.notify.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.TransactionProvider
import org.jooq.impl.DefaultConfiguration
import org.jooq.impl.DefaultDSLContext
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import javax.sql.DataSource

@Primary
@Configuration
class DataSourceConfigTest {
    @Primary
    @Bean("testDataSource")
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
        hikariConfig.connectionTimeout = connectionTimeout.toLong()
        hikariConfig.isAutoCommit = true
        hikariConfig.transactionIsolation = "TRANSACTION_READ_COMMITTED"
        hikariConfig.poolName = "notification-db"
        hikariConfig.maximumPoolSize = maxPoolSize
        return HikariDataSource(hikariConfig)
    }

    @Primary
    @Bean
    fun dslContext(@Qualifier("testDataSource") dataSource: DataSource, transactionProvider: TransactionProvider): DSLContext {
        val config = DefaultConfiguration()
        config.setDataSource(dataSource)
        config.setSQLDialect(SQLDialect.POSTGRES)
        config.setTransactionProvider(transactionProvider)
        return DefaultDSLContext(config)
    }
}
