package com.pss.searchservice.search.controller

import com.pss.searchservice.search.service.ProductSearchService
import io.mockk.every
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class ProductSearchControllerTest(
    @Autowired
    val mockMvc: MockMvc,
    @Autowired
    val productSearchService: ProductSearchService
) {
    private val baseUrl = "/product"

    @Test
    @DisplayName("상품 리스트 조회 성공 200 OK")
    fun `상품 리스트 조회 성공시 200 OK`() {
        mockMvc.perform(
            get(baseUrl)
                .param("page", 0.toString())
                .param("size", 1.toString())
        ).andExpect(status().isOk)
    }

    @Test
    @DisplayName("상품 검색 성공 200 OK")
    fun `상품 검색 성공시 200 OK`() {
        mockMvc.perform(
            get("$baseUrl/search")
                .param("page", 0.toString())
                .param("size", 1.toString())
                .param("q", "test")
        ).andExpect(status().isOk)
    }

    @Test
    @DisplayName("자동 완성 성공 200 OK")
    fun `자동완성 조회 성공시 200 OK`() {
        mockMvc.perform(
            get("$baseUrl/auto-complete")
                .param("q", "test")
        ).andExpect(status().isOk)
    }
    @Test
    @DisplayName("자동 완성 204 No Content")
    fun `자동완성 조회 결과 null 일 경우 204 No Content`() {
        every {productSearchService.autoComplete(any())} returns null

        mockMvc.perform(
            get("$baseUrl/auto-complete")
                .param("q", "test")
        ).andExpect(status().isNoContent)
    }
}