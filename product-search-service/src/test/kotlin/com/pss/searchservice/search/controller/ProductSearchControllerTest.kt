package com.pss.searchservice.search.controller

import com.pss.searchservice.search.service.ProductSearchService
import org.springframework.test.web.servlet.MockMvc

internal class ProductSearchControllerTest(
    val mockMvc: MockMvc
) {
    lateinit var productSearchService: ProductSearchService
    private val baseUrl = "/product"

}