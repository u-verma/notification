package com.notify.api.user.service

import com.notify.api.exception.UserDoesNotExistException
import com.notify.api.user.domain.UserDetailsResponse
import com.notify.api.user.domain.UserInformation
import com.notify.api.user.domain.UserProfile
import com.notify.api.user.repository.UserProfileRepository
import com.notify.common.NotificationStatus
import com.notify.common.domain.EmailId
import com.notify.common.domain.UserId
import com.notify.databases.model.tables.records.UserProfileRecord
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Service
class UserProfileService(
        private val userProfileRepository: UserProfileRepository
) {

    fun getUserDetail(emailId: String): UserDetailsResponse {
        var userDetailsResponse: UserDetailsResponse? = null
        userProfileRepository.fetchByEmailId(emailId)
                .map { record: UserProfileRecord ->
                    val userProfile = record.toUserProfile()
                    userProfile
                    userDetailsResponse = UserDetailsResponse(
                            userProfile.userId.value.toString(),
                            userProfile.emailId.value,
                            userProfile.notificationStatus.name
                    )
                }.orElseThrow { UserDoesNotExistException("User with email id $emailId Doesn't exist ") }
        return userDetailsResponse!!
    }

    fun createUserProfile(userInformation: UserInformation): Boolean {
        return userProfileRepository.save(userInformation.toUserProfile())
    }

    @Transactional
    fun enableSubscription(userId: String) {
        userProfileRepository.fetchByUserId(userId)
                .map { record: UserProfileRecord ->
                    val updatedUserProfile = getUpdatedNotificationModel(
                            record.toUserProfile(),
                            NotificationStatus.SUBSCRIBED
                    )
                    userProfileRepository.save(updatedUserProfile)
                }.orElseThrow { UserDoesNotExistException("User $userId Doesn't exist ") }

    }

    @Transactional
    fun disableSubscription(userId: String) {
        userProfileRepository.fetchByUserId(userId)
                .map { record: UserProfileRecord ->
                    val updatedUserProfile = getUpdatedNotificationModel(
                            record.toUserProfile(),
                            NotificationStatus.UNSUBSCRIBED)
                    userProfileRepository.save(updatedUserProfile)
                }.orElseThrow { UserDoesNotExistException("User $userId Doesn't exist ") }
    }
}

private fun UserInformation.toUserProfile() =
        UserProfile(
                UserId(UUID.randomUUID()),
                this.fullName,
                EmailId(this.emailId),
                this.countryCode,
                this.notificationStatus,
                // TODO:  get the UTC TimeZone of User
                LocalDateTime.now(),
                LocalDateTime.now()
        )

private fun UserProfileRecord.toUserProfile(): UserProfile =
        UserProfile(
                UserId(UUID.fromString(this.userId)),
                this.fullName,
                EmailId(this.emailId),
                this.country,
                when (notificationStatus) {
                    true -> NotificationStatus.SUBSCRIBED
                    else -> NotificationStatus.UNSUBSCRIBED
                },
                notificationTsUtc.toLocalDateTime(),
                lastEmailSentTsUtc.toLocalDateTime()
        )

private fun getUpdatedNotificationModel(userProfile: UserProfile,
                                        notificationStatus: NotificationStatus) =
        UserProfile(
                userProfile.userId,
                userProfile.fullName,
                userProfile.emailId,
                userProfile.countryCode,
                notificationStatus,
                userProfile.notificationTsUtc,
                userProfile.lastEmailSentTsUtc
        )

