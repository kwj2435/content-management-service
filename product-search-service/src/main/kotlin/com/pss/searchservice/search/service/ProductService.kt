package com.pss.searchservice.search.service

import com.pss.searchservice.search.data.indexed.ProductIndexed
import com.pss.searchservice.search.data.indexed.ProductIndexedRepository
import com.pss.searchservice.search.data.search.ProductSearchCondition
import org.springframework.context.annotation.Profile
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.stereotype.Service

interface ProductService{
    fun list(pageable: Pageable, productSearchCondition: ProductSearchCondition): Page<ProductIndexed>
    fun search(q: String, pageable: Pageable): Page<ProductIndexed>
    fun autoComplete(q: String): List<ProductIndexed>?
}

@Profile("!test")
@Service
class ProductServiceImpl(
    private val productIndexedRepository: ProductIndexedRepository,
    private val productSearchService: ProductSearchService
): ProductService {
    override fun list(pageable: Pageable, productSearchCondition: ProductSearchCondition): Page<ProductIndexed> {
        return productIndexedRepository.findAll(pageable)
    }

    override fun search(q: String, pageable: Pageable): Page<ProductIndexed> {
        return productSearchService.search(q, pageable)
    }

    override fun autoComplete(q: String): List<ProductIndexed>? {
        return productSearchService.autoComplete(q)
    }
}

@Profile("test")
@Service
class ProductServiceMock: ProductService {
    override fun list(pageable: Pageable, productSearchCondition: ProductSearchCondition): Page<ProductIndexed> {
        TODO("Not yet implemented")
    }

    override fun search(q: String, pageable: Pageable): Page<ProductIndexed> {
        TODO("Not yet implemented")
    }

    override fun autoComplete(q: String): List<ProductIndexed>? {
        TODO("Not yet implemented")
    }

}