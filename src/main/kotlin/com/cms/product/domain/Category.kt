package com.cms.product.domain

import com.cms.product.data.CategoryResult
import com.cms.product.entity.CategoryEntityRepository
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository
import java.time.ZonedDateTime

data class Category(
    val id: Long,
    val categoryName: String,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
) {
    fun result() =
        CategoryResult(
            id = this.id,
            categoryName = this.categoryName,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
}

interface CategoryRepository {
    fun get(categoryId: Long): Category
}

@Profile("!test")
@Repository
class CategoryRepositoryImpl(
    private val categoryEntityRepository: CategoryEntityRepository
): CategoryRepository {
    override fun get(categoryId: Long): Category {
        return categoryEntityRepository.findById(categoryId)
            .orElseThrow { throw NoSuchElementException("해당 CategoryId : ${categoryId}는 존재하지 않습니다.") }
            .domain()
    }
}

@Profile("test")
class CategoryRepositoryMock(): CategoryRepository {
    override fun get(categoryId: Long): Category {
        return Category(
            id = 1,
            categoryName = "test",
            createdAt = ZonedDateTime.now(),
            updatedAt = ZonedDateTime.now()
        )
    }
}

