package com.notify.api.exception

import java.time.LocalDateTime

data class ErrorDetails(
        val timestamp: LocalDateTime,
        val message: String,
        val details: String)