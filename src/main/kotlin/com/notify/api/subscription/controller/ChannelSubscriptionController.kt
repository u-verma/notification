package com.notify.api.subscription.controller

import com.notify.api.subscription.domain.RedditChannelInfo
import com.notify.api.subscription.service.ChannelSubscriptionService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/subscribe")
class ChannelSubscriptionController(private val channelSubscriptionService: ChannelSubscriptionService) {

    private val logger = LoggerFactory.getLogger(ChannelSubscriptionController::class.java)

    @PutMapping("/addChannel")
    fun addSubscriptionChannelForUser(@RequestBody redditChannelInfo: RedditChannelInfo): ResponseEntity<String> {
        try {
            channelSubscriptionService.addChannelSubscriptionForUser(redditChannelInfo)
        } catch (ex: Exception) {
            logger.error("Error occurred during operation $ex")
            throw ex
        }
        return ResponseEntity.ok().build()
    }

    @PutMapping("/updateChannel")
    fun updateSubscriptionChannelForUser(@RequestBody redditChannelInfo: RedditChannelInfo): ResponseEntity<String> {
        try {
            channelSubscriptionService.updateChannelSubscriptionForUser(redditChannelInfo)
        } catch (ex: Exception) {
            logger.error("Error occurred during operation $ex")
            throw ex
        }
        return ResponseEntity.ok().build()
    }
}