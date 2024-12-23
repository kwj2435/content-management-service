package com.cms.product.data

import com.cms.product.domain.enums.ProductStatus
import java.time.ZonedDateTime

data class ProductInput(
    val name: String,
    val categoryId: Long,
    val status: ProductStatus,
    val salesStartedAt: ZonedDateTime? = null,
    val salesFinishedAt: ZonedDateTime? = null
)
