package com.adamglin.qdrantkt

import io.ktor.client.*
import io.ktor.client.plugins.*

class QdrantClient(
    httpClient: HttpClient,
    private val url: String
) {
    internal val httpClient = httpClient.config {
        defaultRequest {
            host = this@QdrantClient.url
        }
    }
}