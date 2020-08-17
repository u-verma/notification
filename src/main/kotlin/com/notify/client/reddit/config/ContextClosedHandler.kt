package com.notify.client.reddit.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextClosedEvent
import org.springframework.stereotype.Component
import java.util.concurrent.ExecutorService
import java.util.concurrent.TimeUnit

@Component
class ContextClosedHandler(
        @Qualifier("redditExecutorService") private val executorService: ExecutorService
) : ApplicationListener<ContextClosedEvent> {

    override fun onApplicationEvent(event: ContextClosedEvent) {
        executorService.awaitTermination(1000, TimeUnit.MILLISECONDS)
    }
}
