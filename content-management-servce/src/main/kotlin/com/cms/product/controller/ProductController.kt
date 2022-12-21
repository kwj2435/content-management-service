package com.cms.product.controller

import com.cms.common.data.ApiResponse
import com.cms.product.data.ProductInput
import com.cms.product.data.indexed.ProductIndexedRepository
import com.cms.product.domain.ProductRepository
import com.cms.product.entity.ProductEntityRepository
import com.cms.product.service.ProductService
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates
import org.springframework.data.elasticsearch.core.query.IndexQuery
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

// todo exception 처리
// todd APIResponse Format 정리
@RestController
@RequestMapping("/product")
class ProductController(
    private val productService: ProductService,
    private val productIndexedRepository: ProductIndexedRepository,
    val productEntityRepository: ProductEntityRepository
) {
    @GetMapping("")
    fun list(pageable: Pageable) =
        productService.list(pageable)?.let { ResponseEntity.ok().body(it) }
            ?: throw NoSuchElementException("데이터를 찾을 수 없습니다.")

    @GetMapping("/{productId}")
    fun get(@PathVariable("productId") productId: Long) =
        productService.get(productId)?.let { ResponseEntity.ok().body(ApiResponse(it)) }
            ?: throw NoSuchElementException("productId : ${productId}를 찾을 수 없습니다.")

    @PostMapping
    fun create(@RequestBody input: ProductInput) =
        ResponseEntity.ok().body(ApiResponse(productService.create(input)))

    @PutMapping("/{productId}")
    fun update(@PathVariable("productId") productId: Long, @RequestBody ProductInput: ProductInput) =
        productService.update(productId, ProductInput)?.let { ResponseEntity.ok().body(ApiResponse(it)) }
            ?: throw NoSuchElementException("productId : ${productId}를 찾을 수 없습니다.")

    @DeleteMapping("/{productId}")
    fun delete(@PathVariable("productId") productId: Long) =
        productService.delete(productId)?.let { ResponseEntity.ok().body(ApiResponse(it)) }
            ?: throw NoSuchElementException("productId : ${productId}를 찾을 수 없습니다.")

    @GetMapping("test")
    fun test() {
        val list = productEntityRepository.findAll().map { it.indexed() }
        val test = productIndexedRepository.saveAll(list)
    }
}