package com.adamglin.qdrantkt.collections

import com.adamglin.qdrantkt.QdrantClient
import com.adamglin.qdrantkt.domain.*
import com.adamglin.qdrantkt.utils.qdrantResultOrThrow
import io.ktor.client.request.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

/**
 * Create a new collection.
 * @param collectionName Name of the new collection,
 * @param timeout Wait for the operation commit timeout in seconds. If timeout is reached - the request will return with a service error.
 */
suspend fun QdrantClient.createCollection(
    collectionName: String,
    vectors: Map<String, VectorParams>,
    shardNumber: Int? = null,
    shardingMethod: ShardingMethod? = null,
    replicationFactor: Int? = null,
    writeConsistencyFactor: Int? = null,
    onDiskPayload: Boolean? = null,
    hnswConfig: HnswConfig? = null,
    walConfig: WalConfigDiff? = null,
    optimizersConfig: OptimizersConfigDiff? = null,
): Boolean = httpClient.put("/collections/$collectionName") {
    setBody(
        CreateCollectionRequest(
            vectors = vectors,
            shardNumber = shardNumber,
            shardingMethod = shardingMethod,
            replicationFactor = replicationFactor,
            writeConsistencyFactor = writeConsistencyFactor,
            onDiskPayload = onDiskPayload,
            hnswConfig = hnswConfig,
            walConfig = walConfig,
            optimizersConfig = optimizersConfig
        )
    )
}.qdrantResultOrThrow()

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
internal data class CreateCollectionRequest(
    val vectors: Map<String, VectorParams>,
    @SerialName("shard_number")
    val shardNumber: Int? = null,
    @SerialName("sharding_method")
    val shardingMethod: ShardingMethod? = null,
    @SerialName("replication_factor")
    val replicationFactor: Int? = null,
    @SerialName("write_consistency_factor")
    val writeConsistencyFactor: Int? = null,
    @SerialName("on_disk_payload")
    val onDiskPayload: Boolean? = null,
    @SerialName("hnsw_config")
    val hnswConfig: HnswConfig? = null,
    @SerialName("wal_config")
    val walConfig: WalConfigDiff? = null,
    @SerialName("optimizers_config")
    val optimizersConfig: OptimizersConfigDiff? = null,
)