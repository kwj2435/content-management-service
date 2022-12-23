package com.pss.searchservice.search.controller

import com.pss.searchservice.search.service.ProductSearchService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
internal class ProductSearchControllerTest(
    @Autowired
    val mockMvc: MockMvc
) {
    private val baseUrl = "/product"

    @Test
    @DisplayName("상품 리스트 조회 성공 200 OK")
    fun listSuccessTest() {
        mockMvc.perform(
            get(baseUrl)
                .param("page", 0.toString())
                .param("size", 1.toString())
        ).andExpect(status().isOk)
    }

    @Test
    @DisplayName("상품 리스트 없을 경우 204 NoContent")
    fun listNoContentTest() {

    }

    @Test
    @DisplayName("상품 검색 성공 200 OK")
    fun searchSuccessTest() {

    }

    @Test
    @DisplayName("상품 검색 성공 204 NoContent")
    fun searchNoContent() {

    }
}