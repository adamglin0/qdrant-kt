package com.adamglin.qdrantkt.collections

import com.adamglin.qdrantkt.QdrantClient
import com.adamglin.qdrantkt.domain.ShardingMethod
import com.adamglin.qdrantkt.domain.VectorParams
import kotlin.time.Duration

/**
 * Create a new collection.
 * @param collectionName Name of the new collection,
 * @param timeout Wait for the operation commit timeout in seconds. If timeout is reached - the request will return with a service error.
 */
suspend fun QdrantClient.createCollection(
    collectionName: String,
    timeout: Duration? = null,
    vectors: Map<String, VectorParams>? = null,
    shardNumber: Int? = null,
    shardingMethod: ShardingMethod = ShardingMethod.Auto,
    replicationFactor: Int = 1,
) {
    require(replicationFactor < 1) { "Replication factor must be greater than 0" }
}