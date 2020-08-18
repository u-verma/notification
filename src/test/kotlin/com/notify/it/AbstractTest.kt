package com.notify.it

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.notify.NotificationApplication
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import java.io.IOException

@SpringBootTest(
        webEnvironment = RANDOM_PORT,
        classes = [NotificationApplication::class],
        properties = ["spring.main.allow-bean-definition-overriding=true"]
)
@AutoConfigureMockMvc
abstract class AbstractTest {

    @Throws(JsonProcessingException::class)
    protected fun mapToJson(obj: Any?): String? {
        val objectMapper = ObjectMapper()
        return objectMapper.writeValueAsString(obj)
    }

    @Throws(JsonParseException::class, JsonMappingException::class, IOException::class)
    protected fun <T> mapFromJson(json: String?, clazz: Class<T>?): T {
        val objectMapper = ObjectMapper()
        return objectMapper.readValue(json, clazz)
    }

}