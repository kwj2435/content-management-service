package com.pss.searchservice.search.service

import io.mockk.mockk
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

class ProductServiceImplTest{
    private lateinit var productSearchService: ProductSearchService
    private lateinit var productService: ProductService

    @BeforeEach
    fun setUp() {
        productSearchService = mockk()
        productService = ProductServiceImpl(
            productSearchService = productSearchService
        )
    }

    @Test
    fun list() {
    }

    @Test
    fun search() {
    }

    @Test
    fun autoComplete() {
    }
}