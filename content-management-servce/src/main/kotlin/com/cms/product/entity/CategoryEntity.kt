package com.cms.product.entity

import com.cms.product.domain.Category
import org.springframework.data.jpa.repository.JpaRepository
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "category")
class CategoryEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var categoryName: String,
    var createdAt: ZonedDateTime,
    var updatedAt: ZonedDateTime
) {
    fun domain() =
        Category(
            id = this.id,
            categoryName = this.categoryName,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
}

interface CategoryEntityRepository: JpaRepository<CategoryEntity, Long>