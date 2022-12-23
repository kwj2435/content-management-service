package com.pss.searchservice.search.data.search

import com.pss.searchservice.search.domain.enums.ProductStatus

data class ProductSearchCondition(
    val categoryId: Long? = null,
    val status: ProductStatus? = null
)