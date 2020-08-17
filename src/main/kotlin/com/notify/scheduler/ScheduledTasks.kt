package com.notify.scheduler

import com.notify.api.user.repository.UserProfileRepository
import com.notify.client.email.service.EmailClientImpl
import com.notify.client.email.service.EmailService
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

import java.text.SimpleDateFormat
import java.util.Date

@Component
class ScheduledTasks(val emailService: EmailService,
val userProfileRepository: UserProfileRepository) {
    private val logger = LoggerFactory.getLogger(ScheduledTasks::class.java)

    private val dateFormat = SimpleDateFormat("HH:mm:ss")

    @Scheduled(cron = "0 * * ? * *")
    fun reportCurrentTime() {
        val userList = userProfileRepository.getUsersForSchedulingEmail()
        logger.info("The User List for scheduling email: ${userList.isEmpty()}")
        if(userList.isNotEmpty()){
            emailService.execute(userList!!)
        }
    }
}