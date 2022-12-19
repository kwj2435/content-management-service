package com.cms.product.data

import java.time.ZonedDateTime

data class CategoryResult(
    val id: Long,
    val categoryName: String,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
)
