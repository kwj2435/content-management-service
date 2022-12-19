package com.pss.searchservice.search.data.indexed

import com.pss.searchservice.search.domain.enums.ProductStatus
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import java.time.ZonedDateTime

// todo sdk로 묶자
@Document(indexName = "product")
class ProductIndexed(
    @Id
    val id: Long = 0,
    val name: String,
    val category: CategoryIndexed,
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