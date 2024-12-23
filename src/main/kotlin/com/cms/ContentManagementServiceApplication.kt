package com.cms

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ContentManagementServiceApplication

fun main(args: Array<String>) {
    runApplication<ContentManagementServiceApplication>(*args)
}
