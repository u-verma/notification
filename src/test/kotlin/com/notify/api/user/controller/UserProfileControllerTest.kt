package com.notify.api.user.controller

import com.notify.api.subscription.domain.RedditChannelInfo
import com.notify.api.subscription.service.ChannelSubscriptionService
import com.notify.api.user.domain.UserDetailsResponse
import com.notify.api.user.domain.UserInformation
import com.notify.api.user.service.UserProfileService
import com.notify.common.NotificationStatus
import com.notify.it.AbstractTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.internal.verification.Times
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.RequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

class UserProfileControllerTest : AbstractTest() {
    @Autowired
    private var mockMvc: MockMvc? = null

    @MockBean
    private var userProfileService: UserProfileService? = null

    @Test
    fun shouldCreateUserProfile() {

        val userInfo = UserInformation(
                "TestName",
                "TestEmai@gmail.com",
                "DE",
                NotificationStatus.SUBSCRIBED
        )

        val requestData = this.mapToJson(userInfo)

        val requestBuilder: RequestBuilder = MockMvcRequestBuilders
                .put("/api/user/create")
                .accept(MediaType.APPLICATION_JSON).content(requestData!!)
                .contentType(MediaType.APPLICATION_JSON)

        val mvcResult = mockMvc!!.perform(requestBuilder).andReturn()
        val response = mvcResult.response
        Assertions.assertThat(HttpStatus.OK.value()).isEqualTo(response.status)
        Mockito.verify(userProfileService, Times(1))
                ?.createUserProfile(userInfo)
    }

    @Test
    fun shouldEnableSubscriptionForUser() {

        val userId = "TestEmai@gmail.com";
        val requestBuilder: RequestBuilder = MockMvcRequestBuilders
                .put("/api/user/enableSubscription")
                .accept(MediaType.APPLICATION_JSON).content(userId)
                .contentType(MediaType.APPLICATION_JSON)

        val mvcResult = mockMvc!!.perform(requestBuilder).andReturn()
        val response = mvcResult.response
        Assertions.assertThat(HttpStatus.OK.value()).isEqualTo(response.status)
        Mockito.verify(userProfileService, Times(1))
                ?.enableSubscription(userId)
    }

    @Test
    fun shouldDisableSubscriptionForUser() {

        val userId = "TestEmai@gmail.com";
        val requestBuilder: RequestBuilder = MockMvcRequestBuilders
                .put("/api/user/disableSubscription")
                .accept(MediaType.APPLICATION_JSON).content(userId)
                .contentType(MediaType.APPLICATION_JSON)

        val mvcResult = mockMvc!!.perform(requestBuilder).andReturn()
        val response = mvcResult.response
        Assertions.assertThat(HttpStatus.OK.value()).isEqualTo(response.status)
        Mockito.verify(userProfileService, Times(1))
                ?.disableSubscription(userId)
    }

    @Test
    fun shouldGetUserDetails() {

        val userInfo = UserDetailsResponse(
                "TestName",
                "TestEmai@gmail.com",
                "SUBSCRIBED"
        )

        `when`(userProfileService?.getUserDetail("TestEmai@gmail.com"))
                .thenReturn(userInfo)

        val requestBuilder: RequestBuilder = MockMvcRequestBuilders
                .get("/api/user/fetch/TestEmai@gmail.com")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)

        val mvcResult = mockMvc!!.perform(requestBuilder).andReturn()
        val response = mvcResult.response
        Assertions.assertThat(HttpStatus.OK.value()).isEqualTo(response.status)
        Mockito.verify(userProfileService, Times(1))
                ?.getUserDetail("TestEmai@gmail.com")
    }
}