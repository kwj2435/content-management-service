package com.pss.searchservice.search.service

import com.pss.searchservice.config.EsConfig
import com.pss.searchservice.search.data.indexed.ProductIndexed
import com.pss.searchservice.search.data.indexed.ProductIndexedRepository
import com.pss.searchservice.search.data.search.ProductSearchCondition
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.index.query.QueryBuilders.*
import org.springframework.context.annotation.Profile
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.SearchHitSupport
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
import org.springframework.stereotype.Service

interface ProductSearchService {
    fun search(search: ProductSearchCondition, pageable: Pageable): Page<ProductIndexed>
    fun search(q: String, pageable: Pageable): Page<ProductIndexed>
    fun autoComplete(q: String): List<ProductIndexed>?
}

@Profile("!test")
@Service
class ProductSearchServiceImpl(
    private val esConfig: EsConfig,
    private val elasticsearchOperations: ElasticsearchOperations
): ProductSearchService {
    override fun search(search: ProductSearchCondition, pageable: Pageable): Page<ProductIndexed> {
        val query = NativeSearchQueryBuilder().withQuery(
            boolQuery().must(
                search.categoryId?.let { matchQuery("category.id", search.categoryId)} ?: matchAllQuery()
            ).must(
                search.status?.let { matchQuery("status", search.status) } ?: matchAllQuery()
            )
        ).withPageable(pageable).build()

        val result = elasticsearchOperations.search(
            query,
            ProductIndexed::class.java,
            IndexCoordinates.of(esConfig.indexName)
        )

        return SearchHitSupport.searchPageFor(result, pageable).map { it.content }
    }

    override fun search(q: String, pageable: Pageable): Page<ProductIndexed> {
        val query = NativeSearchQueryBuilder()
            .withQuery(matchQuery("name", q))
            .withPageable(pageable)
            .build()
        val result =
            elasticsearchOperations.search(
                query,
                ProductIndexed::class.java,
                IndexCoordinates.of(esConfig.indexName)
            )

        return SearchHitSupport.searchPageFor(result, pageable).map { it.content }
    }

    override fun autoComplete(q: String): List<ProductIndexed>? {
        val query = prefixQuery("name", q)
        val searchResult = elasticsearchOperations.search(
            NativeSearchQueryBuilder().withQuery(query).build(),
            elasticsearchOperations::class.java,
            IndexCoordinates.of(esConfig.indexName)
        )

        return SearchHitSupport.unwrapSearchHits(searchResult) as List<ProductIndexed>
    }
}

@Profile("test")
@Service
class ProductSearchServiceMock(): ProductSearchService {
    override fun search(search: ProductSearchCondition, pageable: Pageable): Page<ProductIndexed> {
        TODO("Not yet implemented")
    }

    override fun search(q: String, pageable: Pageable): Page<ProductIndexed> {
        TODO("Not yet implemented")
    }

    override fun autoComplete(q: String): List<ProductIndexed>? {
        TODO("Not yet implemented")
    }
}