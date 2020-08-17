package com.notify.client.reddit.config

import com.notify.client.reddit.domain.RedditData
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.ExecutorCompletionService
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@Configuration
class ExecutorServiceConfig {

    @Bean("completionService")
    fun creatCompletionService() = ExecutorCompletionService<RedditData>(creatExecutorThreadPool())

    @Bean("redditExecutorService")
    fun creatExecutorThreadPool(): ExecutorService = Executors.newFixedThreadPool(5)

}