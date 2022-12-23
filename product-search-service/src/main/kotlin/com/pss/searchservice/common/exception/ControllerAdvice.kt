package com.cms.common.exception

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerAdvice {
    val log: Logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(value = [NoSuchElementException::class])
    fun noSuchElementExceptionHandler(e: NoSuchElementException): ResponseEntity<ExceptionMessage> {
        return noContent(e)
    }

    private fun noContent(e: Exception): ResponseEntity<ExceptionMessage> {
        log.error("", e)
        return ResponseEntity.noContent().build()
    }

    @ExceptionHandler
    fun defaultExceptionHandler(e: Exception): ResponseEntity<ExceptionMessage> {
        return ResponseEntity
            .internalServerError()
            .body(
                ExceptionMessage(
                    code = HttpStatus.INTERNAL_SERVER_ERROR,
                    message = "서버 내부에서 오류가 발생했습니다.",
                    detail = e.message
                )
            )
    }
}