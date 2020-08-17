package com.notify.client.reddit.config

import org.apache.http.client.config.RequestConfig
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy
import org.apache.http.impl.client.HttpClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HttpClientConfig(private val httpConnectionPool: HttpConnectionPool) {

    @Bean(destroyMethod = "close")
    fun creatCloseableHttpClient(@Value("\${spring.connection.request.timeout.millis}")
                                 requestTimeout: Int,
                                 @Value("\${spring.connection.connect.timeout.millis}")
                                 connectTimeout: Int,
                                 @Value("\${spring.connection.socket.timeout.millis}")
                                 socketTimeout: Int
    ): CloseableHttpClient {

        val requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(requestTimeout)
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout).build()

        return HttpClients.custom().setDefaultRequestConfig(requestConfig)
                .setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy())
                .setConnectionManagerShared(true)
                .setConnectionManager(httpConnectionPool.poolingConnectionManager).build()
    }
}