package com.cms.product.entity

import com.cms.product.data.indexed.ProductIndexed
import com.cms.product.domain.Product
import com.cms.product.domain.enums.ProductStatus
import org.springframework.data.jpa.repository.JpaRepository
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "product")
class ProductEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var name: String,
    @OneToOne
    @JoinColumn(name = "categoryId")
    var category: CategoryEntity,
    @Enumerated(EnumType.STRING)
    var status: ProductStatus,
    var salesStartedAt: ZonedDateTime? = null,
    var salesFinishedAt: ZonedDateTime? = null,
    var createdAt: ZonedDateTime,
    var updatedAt: ZonedDateTime
) {
    fun domain() =
        Product(
            id = this.id,
            name = this.name,
            category = this.category.domain(),
            status = this.status,
            salesStartedAt = this.salesStartedAt,
            salesFinishedAt = this.salesFinishedAt,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )

    fun indexed() =
        ProductIndexed(
            id = this.id,
            name = this.name,
            category = this.category.domain(),
            status = this.status,
            salesStartedAt = this.salesStartedAt,
            salesFinishedAt = this.salesFinishedAt,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
}

interface ProductEntityRepository: JpaRepository<ProductEntity, Long>