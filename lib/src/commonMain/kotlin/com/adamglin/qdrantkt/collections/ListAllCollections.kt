package com.adamglin.qdrantkt.collections

import com.adamglin.qdrantkt.QdrantClient
import com.adamglin.qdrantkt.domain.QdrantResponse
import com.adamglin.qdrantkt.utils.qdrantResultOrThrow
import io.ktor.client.request.*
import kotlinx.serialization.Serializable

/**
 * Returns a list of all existing collections.
 */
suspend fun QdrantClient.listAllCollections(): List<String> {
    return httpClient.get("/collections").qdrantResultOrThrow<Collections>().collections.map { it.name }
}

@Serializable
data class Collections(val collections: List<Collection>) {
    @Serializable
    data class Collection(val name: String)
}
