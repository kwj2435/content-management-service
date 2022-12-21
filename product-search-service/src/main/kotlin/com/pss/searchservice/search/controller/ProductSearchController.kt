package com.pss.searchservice.search.controller

import com.pss.searchservice.common.data.ApiResponse
import com.pss.searchservice.search.data.search.ProductSearchCondition
import com.pss.searchservice.search.service.ProductSearchService
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product")
class ProductSearchController(
    private val productSearchService: ProductSearchService
){
    @GetMapping
    fun list(pageable: Pageable, condition: ProductSearchCondition) =
        ResponseEntity.ok().body(productSearchService.search(condition, pageable))

    @GetMapping("/search")
    fun search(q: String, pageable: Pageable) {
        ResponseEntity.ok().body(productSearchService.search(q, pageable))
    }

    @GetMapping("/auto-complete")
    fun autoComplete(q: String) {
        productSearchService.autoComplete(q)?.let { ResponseEntity.ok().body(ApiResponse(it)) }
            ?:throw NoSuchElementException("조회된 결과가 없습니다")
    }
}