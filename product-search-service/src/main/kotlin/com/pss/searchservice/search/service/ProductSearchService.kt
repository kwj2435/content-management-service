package com.pss.searchservice.search.service

import com.pss.searchservice.search.data.indexed.ProductIndexed
import com.pss.searchservice.search.data.indexed.ProductIndexedRepository
import com.pss.searchservice.search.data.search.ProductSearchCondition
import org.springframework.context.annotation.Profile
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

interface ProductSearchService{
    fun list(pageable: Pageable, productSearchCondition: ProductSearchCondition): Page<ProductIndexed>
    fun search()
}

@Profile("!test")
@Service
class ProductSearchServiceImpl(
    private val productIndexedRepository: ProductIndexedRepository
): ProductSearchService {
    override fun list(pageable: Pageable, productSearchCondition: ProductSearchCondition): Page<ProductIndexed> {
        TODO("Not yet implemented")
    }

    override fun search() {
        TODO("Not yet implemented")
    }

}

@Profile("test")
@Service
class ProductSearchServiceMock: ProductSearchService {
    override fun list(pageable: Pageable, productSearchCondition: ProductSearchCondition): Page<ProductIndexed> {
        TODO("Not yet implemented")
    }

    override fun search() {
        TODO("Not yet implemented")
    }
}