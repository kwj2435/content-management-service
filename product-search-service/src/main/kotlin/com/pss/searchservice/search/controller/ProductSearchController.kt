package com.pss.searchservice.search.controller

import com.pss.searchservice.search.data.search.ProductSearchCondition
import com.pss.searchservice.search.service.ProductSearchService
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product")
class ProductSearchController(
    private val productSearchService: ProductSearchService
){

    @GetMapping
    fun list(pageable: Pageable, search: ProductSearchCondition) =
        ResponseEntity.ok().body(productSearchService.list(pageable, search))

    @GetMapping("/search")
    fun search(q: String) {
    }
}