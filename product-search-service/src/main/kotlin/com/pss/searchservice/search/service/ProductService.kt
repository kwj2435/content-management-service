package com.pss.searchservice.search.service

import com.pss.searchservice.search.data.indexed.CategoryIndexed
import com.pss.searchservice.search.data.indexed.ProductIndexed
import com.pss.searchservice.search.data.indexed.ProductIndexedRepository
import com.pss.searchservice.search.data.search.ProductSearchCondition
import com.pss.searchservice.search.domain.enums.ProductStatus
import org.springframework.context.annotation.Profile
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

interface ProductService{
    fun list(productSearchCondition: ProductSearchCondition, pageable: Pageable): Page<ProductIndexed>
    fun search(q: String, pageable: Pageable): Page<ProductIndexed>
    fun autoComplete(q: String): List<ProductIndexed>?
}

@Profile("!test")
@Service
class ProductServiceImpl(
    private val productSearchService: ProductSearchService
): ProductService {
    override fun list(condition: ProductSearchCondition, pageable: Pageable): Page<ProductIndexed> {
        return productSearchService.search(condition.search, pageable)
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
    // todo Test용 객체 생성 클래스 별도 생성
    override fun list(productSearchCondition: ProductSearchCondition, pageable: Pageable): Page<ProductIndexed> {
        return PageImpl(
            listOf(
                ProductIndexed(
                    id = 0,
                    name = "test",
                    category = CategoryIndexed(
                        id = 1,
                        categoryName = "testCategory",
                    ),
                    status = ProductStatus.SALE,
                    createdAt = ZonedDateTime.now(),
                    updatedAt = ZonedDateTime.now()
                )
            )
        )
    }

    override fun search(q: String, pageable: Pageable): Page<ProductIndexed> {
        TODO("Not yet implemented")
    }

    override fun autoComplete(q: String): List<ProductIndexed>? {
        TODO("Not yet implemented")
    }

}