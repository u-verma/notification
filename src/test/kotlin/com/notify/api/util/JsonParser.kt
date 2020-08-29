package com.notify.api.util

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.IOException


@Throws(JsonProcessingException::class)
fun mapToJson(obj: Any?): String? {
    val objectMapper = ObjectMapper()
    return objectMapper.writeValueAsString(obj)
}

@Throws(JsonParseException::class, JsonMappingException::class, IOException::class)
fun <T> mapFromJson(json: String?, clazz: Class<T>?): T {
    val objectMapper = ObjectMapper()
    return objectMapper.readValue(json, clazz)
}