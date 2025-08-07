package com.adamglin.qdrantkt

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.*

class QdrantClient(
    httpClientEngine: HttpClientEngine,
    url: String,
) {
    internal val httpClient = HttpClient(httpClientEngine) {
        install(ContentNegotiation) {
            json()
        }
        defaultRequest {
            headers {
                contentType(ContentType.Application.Json)
            }
            url(url)
        }
    }
}