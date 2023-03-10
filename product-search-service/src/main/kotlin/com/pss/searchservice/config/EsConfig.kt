package com.pss.searchservice.config

import org.elasticsearch.client.RestHighLevelClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.RestClients
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories

@Configuration
@EnableElasticsearchRepositories
class EsConfig(
    @Value("\${elasticsearch.indexName}")
    val indexName: String,
): AbstractElasticsearchConfiguration() {
    @Value("\${elasticsearch.host}")
    private val esHost: String? = null
    @Value("\${elasticsearch.port}")
    private val esPort: String? = null

    @Bean
    override fun elasticsearchClient(): RestHighLevelClient {
        val clientConfiguration = ClientConfiguration.builder()
            .connectedTo("$esHost:$esPort")
            .build()

        return RestClients.create(clientConfiguration).rest()
    }
}