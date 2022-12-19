package com.cms.product.data

import com.cms.product.domain.enums.ProductStatus
import java.time.ZonedDateTime

data class ProductResult(
    val id: Long,
    val name: String,
    val category: CategoryResult,
    val status: ProductStatus,
    val salesStartedAt: ZonedDateTime? = null,
    val salesFinishedAt: ZonedDateTime? = null,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
)
