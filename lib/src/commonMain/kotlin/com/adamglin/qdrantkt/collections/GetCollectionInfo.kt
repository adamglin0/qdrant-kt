package com.adamglin.qdrantkt.collections

import com.adamglin.qdrantkt.QdrantClient
import com.adamglin.qdrantkt.domain.*
import com.adamglin.qdrantkt.utils.qdrantResultOrThrow
import io.ktor.client.request.*

/**
 * Get detailed information about specified existing collection.
 * @param collectionName Name of the collection to retrieve
 */
suspend fun QdrantClient.getCollectionInfo(collectionName: String): CollectionInfo {
    return httpClient.get("/collections/$collectionName").qdrantResultOrThrow<CollectionInfo>()
} 