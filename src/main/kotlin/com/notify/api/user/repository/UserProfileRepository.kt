package com.notify.api.user.repository

import com.notify.api.user.domain.UserProfile
import com.notify.databases.model.Tables.USER_PROFILE
import com.notify.databases.model.tables.records.UserProfileRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.Optional

@Repository
class UserProfileRepository(private val dslContext: DSLContext) {

    fun save(userProfile: UserProfile): Boolean {
        return 1 == dslContext
                .insertInto(USER_PROFILE)
                .set((userProfile.toUserProfileRecord()))
                .onConflict(USER_PROFILE.EMAIL_ID)
                .doUpdate()
                .set(USER_PROFILE.NOTIFICATION_STATUS, userProfile.notificationStatus.value)
                .execute()
    }

    fun fetchByEmailId(emailId: String): Optional<UserProfileRecord> {
        return dslContext.selectFrom(USER_PROFILE)
                .where(USER_PROFILE.EMAIL_ID.eq(emailId))
                .fetchOptional()
    }

    fun fetchByUserId(userId: String): Optional<UserProfileRecord> {
        return dslContext.selectFrom(USER_PROFILE)
                .where(USER_PROFILE.EMAIL_ID.eq(userId))
                .fetchOptional()
    }

    fun updateLastEmailSentTs(userList: List<String>) {
        dslContext
                .update(USER_PROFILE)
                .set(USER_PROFILE.LAST_EMAIL_SENT_TS_UTC,
                        OffsetDateTime.now(ZoneOffset.UTC))
                .where(USER_PROFILE.USER_ID.`in`(userList))
    }

    fun getUsersForSchedulingEmail(): List<String> {
        return dslContext
                .select(USER_PROFILE.USER_ID)
                .from(USER_PROFILE)
                .where(USER_PROFILE.NOTIFICATION_TS_UTC
                        .between(OffsetDateTime.now(ZoneOffset.UTC),
                                OffsetDateTime.now().plusMinutes(5)),
                        USER_PROFILE.LAST_EMAIL_SENT_TS_UTC
                                .lessThan(
                                        OffsetDateTime.now(ZoneOffset.UTC)
                                                .minusHours(24)
                                ),
                        USER_PROFILE.NOTIFICATION_STATUS.eq(true)
                ).fetch().getValues(USER_PROFILE.USER_ID, String::class.java)

    }
}

private fun UserProfile.toUserProfileRecord(): UserProfileRecord =
        UserProfileRecord(
                this.userId.value.toString(),
                this.fullName,
                this.emailId.value,
                this.countryCode,
                this.notificationStatus.value,
                notificationTsUtc.atOffset(ZoneOffset.UTC),
                lastEmailSentTsUtc.atOffset(ZoneOffset.UTC),
                OffsetDateTime.now(ZoneId.of("UTC")),
                OffsetDateTime.now(ZoneId.of("UTC"))
        )
