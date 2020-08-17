package com.notify.client.reddit.config

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Component
class HttpConnectionPool(@Value("\${spring.pool.max.connection}")
                         val maxTotalConnection: Int,
                         @Value("\${spring.pool.max.per.route}")
                         val defaultMaxPerRoute: Int) {

    var poolingConnectionManager: PoolingHttpClientConnectionManager? = null

    @PostConstruct
    fun initializeConnectionPool() {
        poolingConnectionManager = PoolingHttpClientConnectionManager()
        poolingConnectionManager!!.maxTotal = maxTotalConnection
        poolingConnectionManager!!.defaultMaxPerRoute = defaultMaxPerRoute
    }

    @PreDestroy
    fun shutDownConnectionManager() {
        poolingConnectionManager!!.shutdown()
        poolingConnectionManager = null;
    }
}