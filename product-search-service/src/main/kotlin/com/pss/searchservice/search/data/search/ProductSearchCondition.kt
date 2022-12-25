package com.pss.searchservice.search.data.search

import com.pss.searchservice.search.domain.enums.ProductStatus

data class ProductSearchCondition(
    val search: ProductWhereCondition = ProductWhereCondition()
)

data class ProductWhereCondition(
    var categoryId: Long? = null,
    var status: ProductStatus? = null
)