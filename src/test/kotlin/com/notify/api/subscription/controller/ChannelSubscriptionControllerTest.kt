package com.notify.api.subscription.controller

import com.notify.NotificationApplicationTest
import com.notify.api.subscription.domain.RedditChannelInfo
import com.notify.api.subscription.service.ChannelSubscriptionService
import com.notify.api.util.mapToJson
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.mockito.internal.verification.Times
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.RequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

class ChannelSubscriptionControllerTest : NotificationApplicationTest() {

    @Autowired
    private var mockMvc: MockMvc? = null

    @MockBean
    private var service: ChannelSubscriptionService? = null

    @Test
    fun shouldAddChannelForUser() {

        val redditChannelInfo = getTestData()
        val requestData = mapToJson(redditChannelInfo)

        val requestBuilder: RequestBuilder = MockMvcRequestBuilders
                .put("/api/subscribe/addChannel")
                .accept(MediaType.APPLICATION_JSON).content(requestData!!)
                .contentType(MediaType.APPLICATION_JSON)

        val mvcResult = mockMvc!!.perform(requestBuilder).andReturn()
        val response = mvcResult.response
        assertThat(HttpStatus.OK.value()).isEqualTo(response.status)
        verify(service, Times(1))?.addChannelSubscriptionForUser(redditChannelInfo)
    }

    @Test
    fun shouldUpdateChannelForUser() {

        val redditChannelInfo = getTestData()
        val requestData = mapToJson(redditChannelInfo)

        val requestBuilder: RequestBuilder = MockMvcRequestBuilders.put("/api/subscribe/updateChannel")
                .accept(MediaType.APPLICATION_JSON).content(requestData!!)
                .contentType(MediaType.APPLICATION_JSON)

        val mvcResult = mockMvc!!.perform(requestBuilder).andReturn()
        val response = mvcResult.response
        assertThat(HttpStatus.OK.value()).isEqualTo(response.status)
        verify(service, Times(1))?.updateChannelSubscriptionForUser(redditChannelInfo)
    }

    private fun getTestData() = RedditChannelInfo(
            "dd178821-15c6-45c2-9b97-7df4d1ce73d1",
            "technology"
    )
}