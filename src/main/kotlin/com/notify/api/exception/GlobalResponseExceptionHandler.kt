package com.notify.api.exception

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.LocalDateTime


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
class GlobalResponseExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(
            ex: Exception,
            request: WebRequest
    ): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(LocalDateTime.now(), ex.message!!,
                request.getDescription(false))
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(UserDoesNotExistException::class)
    fun handleUserNotFoundException(
            ex: UserDoesNotExistException,
            request: WebRequest
    ): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(
                LocalDateTime.now(),
                ex.message!!,
                request.getDescription(false)
        )
        return ResponseEntity(errorDetails, HttpStatus.NO_CONTENT)
    }

    @ExceptionHandler(UserAlreadyExistsException::class)
    fun handleUserAlreadyExists(
            ex: UserAlreadyExistsException,
            request: WebRequest
    ): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(
                LocalDateTime.now(),
                ex.message!!,
                request.getDescription(false)
        )
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }
}