package com.cms.product.domain

import com.cms.product.data.ProductInput
import com.cms.product.data.ProductResult
import com.cms.product.domain.enums.ProductStatus
import com.cms.product.entity.CategoryEntityRepository
import com.cms.product.entity.ProductEntity
import com.cms.product.entity.ProductEntityRepository
import org.springframework.context.annotation.Profile
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime

data class Product(
    val id: Long = 0,
    val name: String,
    val category: Category,
    val status: ProductStatus,
    val salesStartedAt: ZonedDateTime? = null,
    val salesFinishedAt: ZonedDateTime? = null,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
) {
    fun result() =
        ProductResult(
            id = this.id,
            name = this.name,
            category = this.category.result(),
            status = this.status,
            salesStartedAt = this.salesStartedAt,
            salesFinishedAt = this.salesFinishedAt,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
}

fun newProduct(productInput: ProductInput, categoryRepository: CategoryRepository) =
    Product(
        name = productInput.name,
        category = categoryRepository.get(productInput.categoryId),
        status = productInput.status,
        salesStartedAt = productInput.salesStartedAt,
        salesFinishedAt = productInput.salesFinishedAt,
        createdAt = ZonedDateTime.now(),
        updatedAt = ZonedDateTime.now()
    )


interface ProductRepository {
    fun list(pageable: Pageable): Page<Product>?
    fun get(productId: Long): Product?
    fun create(product: Product): Product
    fun update(productId: Long, product: Product): Product?
    fun delete(productId: Long): Product?
}

@Profile("!test")
@Repository
class ProductRepositoryImpl(
    private val categoryEntityRepository: CategoryEntityRepository,
    private val productEntityRepository: ProductEntityRepository
): ProductRepository {

    override fun list(pageable: Pageable): Page<Product>? {
        return productEntityRepository.findAll(pageable).map { it.domain() }
    }

    override fun get(productId: Long): Product? {
        return productEntityRepository.findById(productId).orElse(null)?.domain()
    }

    override fun create(product: Product): Product {
        val categoryEntity = categoryEntityRepository.findById(product.category.id).orElseThrow {
            throw NoSuchElementException("해당 CategoryId : ${product.category.id}는 존재하지 않습니다.") }
        val productEntity = ProductEntity(
            name = product.name,
            status = product.status,
            category = categoryEntity,
            salesStartedAt = product.salesStartedAt,
            salesFinishedAt = product.salesFinishedAt,
            createdAt = product.createdAt,
            updatedAt = product.updatedAt
        )

        return productEntityRepository.save(productEntity).domain()
    }

    override fun update(productId: Long, updatedProduct: Product): Product? {
        val savedProduct = productEntityRepository.findById(productId).orElseThrow {
            throw NoSuchElementException("해당 ProductId : ${productId}는 존재하지 않습니다.")
        }
        if(updatedProduct.category.id != savedProduct.category.id){
            savedProduct.category = categoryEntityRepository.findById(savedProduct.category.id).orElseThrow {
                throw NoSuchElementException("해당 CategoryId : ${savedProduct.category.id}는 존재하지 않습니다.")
            }
        }

        savedProduct.name = updatedProduct.name
        savedProduct.status = updatedProduct.status
        savedProduct.salesStartedAt = updatedProduct.salesStartedAt
        savedProduct.salesFinishedAt = updatedProduct.salesFinishedAt
        savedProduct.updatedAt = ZonedDateTime.now()

        return savedProduct.domain()
    }

    override fun delete(productId: Long): Product? {
        val savedProduct = productEntityRepository.findById(productId).orElseThrow{
            throw NoSuchElementException("해당 ProductId : ${productId}는 존재하지 않습니다.")
        }
        productEntityRepository.delete(savedProduct)
        return savedProduct.domain()
    }
}

@Profile("test")
@Repository
class ProductRepositoryMock(): ProductRepository {
    override fun list(pageable: Pageable): Page<Product>? {
        TODO("Not yet implemented")
    }

    override fun get(productId: Long): Product? {
        TODO("Not yet implemented")
    }

    override fun create(product: Product): Product {
        TODO("Not yet implemented")
    }

    override fun update(productId: Long, product: Product): Product? {
        TODO("Not yet implemented")
    }

    override fun delete(productId: Long): Product? {
        TODO("Not yet implemented")
    }
}
