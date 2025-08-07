package com.adamglin.qdrantkt.collections

import com.adamglin.qdrantkt.QdrantClient
import com.adamglin.qdrantkt.domain.QdrantResponse
import com.adamglin.qdrantkt.utils.qdrantResultOrThrow
import io.ktor.client.request.*

/**
 * Drops the specified collection and all associated data in it.
 * @param collectionName The name of the collection to drop.
 */
suspend fun QdrantClient.deleteCollection(collectionName: String): Boolean {
    return httpClient.delete("/collections/${collectionName}").qdrantResultOrThrow()
}