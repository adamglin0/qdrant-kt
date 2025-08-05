package com.adamglin.qdrantkt.collections

import com.adamglin.qdrantkt.QdrantClient
import com.adamglin.qdrantkt.domain.QdrantResponse
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable

/**
 * Returns a list of all existing collections.
 */
suspend fun QdrantClient.listAllCollections(): QdrantResponse<Collections> {
    return httpClient.get("/collections").body()
}

@Serializable
data class Collections(val collections: List<Collection>) {
    @Serializable
    data class Collection(val name: String)
}
