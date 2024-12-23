package com.cms.product.service

import com.cms.product.data.ProductInput
import com.cms.product.data.ProductResult
import com.cms.product.domain.CategoryRepository
import com.cms.product.domain.ProductRepository
import com.cms.product.domain.newProduct
import org.springframework.context.annotation.Profile
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface ProductService {
    fun list(pageable: Pageable): Page<ProductResult>?
    fun get(productId: Long): ProductResult?
    fun create(productInput: ProductInput): ProductResult
    fun update(productId: Long, productInput: ProductInput): ProductResult?
    fun delete(productId: Long): ProductResult?
}

@Profile("!test")
@Transactional(readOnly = true)
@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository
): ProductService {
    override fun list(pageable: Pageable): Page<ProductResult>? {
        return productRepository.list(pageable)?.map { it.result() }
    }

    override fun get(productId: Long): ProductResult? {
        return productRepository.get(productId)?.result()
    }

    override fun create(productInput: ProductInput): ProductResult {
        // todo 바코드 추가 및 중복검사
        isValidSalesDate(productInput)

        return productRepository.create(
            newProduct(
                productInput = productInput,
                categoryRepository = categoryRepository)
        ).result()
    }

    private fun isValidSalesDate(input: ProductInput) {
        if(input.salesStartedAt == null && input.salesFinishedAt == null) return
        if(input.salesStartedAt == null && input.salesFinishedAt != null) {
            throw IllegalArgumentException("상품 시작일 없이 종료일이 설정될 수 없습니다.")
        }
        if(input.salesStartedAt!!.isAfter(input.salesFinishedAt)) {
            throw IllegalArgumentException("상품의 종료일이 시작일보다 앞설 수 없습니다.")
        }
    }

    @Transactional
    override fun update(productId: Long, productInput: ProductInput): ProductResult? {
        isValidSalesDate(productInput)

        return productRepository.update(
            productId = productId,
            product = newProduct(
                productInput = productInput,
                categoryRepository = categoryRepository
            )
        )?.result()
    }

    override fun delete(productId: Long): ProductResult? {
        return productRepository.delete(productId)?.result()
    }
}


@Profile("test")
@Service
class ProductServiceMock(): ProductService {
    override fun list(pageable: Pageable): Page<ProductResult>? {
        TODO("Not yet implemented")
    }

    override fun get(productId: Long): ProductResult? {
        TODO("Not yet implemented")
    }

    override fun create(productInput: ProductInput): ProductResult {
        TODO("Not yet implemented")
    }

    override fun update(productId: Long, productInput: ProductInput): ProductResult? {
        TODO("Not yet implemented")
    }

    override fun delete(productId: Long): ProductResult? {
        TODO("Not yet implemented")
    }
}