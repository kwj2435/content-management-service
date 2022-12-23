package com.pss.searchservice.search.controller

import com.pss.searchservice.common.data.ApiResponse
import com.pss.searchservice.search.data.search.ProductSearchCondition
import com.pss.searchservice.search.service.ProductService
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product")
class ProductSearchController(
    private val productService: ProductService
){
    @GetMapping
    fun list(pageable: Pageable, condition: ProductSearchCondition) =
        ResponseEntity.ok().body(ApiResponse(productService.list(condition, pageable)))

    @GetMapping("/search")
    fun search(q: String, pageable: Pageable) {
        ResponseEntity.ok().body(ApiResponse(productService.search(q, pageable)))
    }

    @GetMapping("/auto-complete")
    fun autoComplete(q: String) {
        productService.autoComplete(q)?.let { ResponseEntity.ok().body(ApiResponse(it)) }
            ?:throw NoSuchElementException("조회된 결과가 없습니다")
    }
}