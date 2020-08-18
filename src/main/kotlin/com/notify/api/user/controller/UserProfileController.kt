package com.notify.api.user.controller

import com.notify.api.exception.UserDoesNotExistException
import com.notify.api.user.domain.UserDetailsResponse
import com.notify.api.user.domain.UserInformation
import com.notify.api.user.service.UserProfileService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserProfileController(private val userProfileService: UserProfileService) {

    private val logger = LoggerFactory.getLogger(UserProfileController::class.java)

    @GetMapping("/fetch/{emailId}")
    fun getUserDetail(@PathVariable emailId: String): UserDetailsResponse {
        try {
            return userProfileService.getUserDetail(emailId)
        } catch (ex: UserDoesNotExistException) {
            logger.error(ex.message)
            throw ex
        }
    }

    @PutMapping("/create")
    fun createUpdateUserProfile(@RequestBody userInformation: UserInformation): ResponseEntity<String> {
        try {
            userProfileService.createUserProfile(userInformation)
        } catch (ex: Exception) {
            logger.error("Error has occurred during update $ex")
            throw Exception("Error has occurred during update ${ex.message}")
        }
        return ResponseEntity.ok().build()
    }

    @PutMapping("/enableSubscription")
    fun enableSubscriptionForUser(@RequestBody userId: String): ResponseEntity<String> {
        try {
            userProfileService.enableSubscription(userId)
        } catch (ex: UserDoesNotExistException) {
            logger.error(ex.message)
            throw ex
        }
        return ResponseEntity.ok().build()
    }

    @PutMapping("/disableSubscription")
    fun disableSubscriptionForUser(@RequestBody userId: String): ResponseEntity<String> {
        try {
            userProfileService.disableSubscription(userId)
        } catch (ex: UserDoesNotExistException) {
            logger.error(ex.message)
            throw ex
        }
        return ResponseEntity.ok().build()
    }
}
