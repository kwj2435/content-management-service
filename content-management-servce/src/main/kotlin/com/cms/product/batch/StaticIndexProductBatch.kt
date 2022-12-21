package com.cms.product.batch

import com.cms.product.data.indexed.ProductIndexedRepository
import com.cms.product.entity.ProductEntityRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates
import org.springframework.data.elasticsearch.core.query.IndexQuery
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder
import org.springframework.scheduling.annotation.Scheduled

@Profile("static-index-product-batch")
class StaticIndexProductBatch(
    private val productEntityRepository: ProductEntityRepository,
    private val productIndexedRepository: ProductIndexedRepository,
    private val elasticsearchOperations: ElasticsearchOperations
) {
    @Scheduled(cron = "*/10 * * * *")
    fun run() {
        val allProduct = productEntityRepository.findAll()
        productIndexedRepository.saveAll(allProduct.map { it.indexed() })
    }
}