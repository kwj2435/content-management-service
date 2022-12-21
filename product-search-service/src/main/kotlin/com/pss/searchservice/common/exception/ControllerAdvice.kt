package com.cms.common.exception

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerAdvice {
    val log: Logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(NoSuchElementException::class)
    fun noSuchElementExceptionHandler(e: NoSuchElementException): ResponseEntity<ExceptionMessage> {
        return noContent(e)
    }

    private fun noContent(e: Exception): ResponseEntity<ExceptionMessage> {
        log.error("", e)
        return ResponseEntity.noContent().build()
    }
}