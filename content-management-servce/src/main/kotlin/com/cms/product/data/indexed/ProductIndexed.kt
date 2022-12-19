package com.cms.product.data.indexed

import com.cms.product.domain.Category
import com.cms.product.domain.enums.ProductStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import java.time.ZonedDateTime
import javax.persistence.Id

@Document(indexName = "product")
class ProductIndexed(
    @Id
    val id: Long = 0,
    val name: String,
    val category: Category,
    val status: ProductStatus,
    @Field(type = FieldType.Date)
    val salesStartedAt: ZonedDateTime? = null,
    @Field(type = FieldType.Date)
    val salesFinishedAt: ZonedDateTime? = null,
    @Field(type = FieldType.Date)
    val createdAt: ZonedDateTime,
    @Field(type = FieldType.Date)
    val updatedAt: ZonedDateTime
)

interface ProductIndexedRepository: ElasticsearchRepository<ProductIndexed, Long>
