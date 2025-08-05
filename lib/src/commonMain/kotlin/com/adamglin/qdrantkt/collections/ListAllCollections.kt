package com.adamglin.qdrantkt.collections

import com.adamglin.qdrantkt.QdrantClient
import com.adamglin.qdrantkt.domain.QdrantResponse
import io.ktor.client.call.*
import io.ktor.client.request.*

/**
 * Returns a list of all existing collections.
 */
suspend fun QdrantClient.listAllCollections(): QdrantResponse<List<Collections>> {
    return httpClient.get("/collections").body()
}

data class Collections(val collections: List<Collection>) {
    data class Collection(val name: String)
}
