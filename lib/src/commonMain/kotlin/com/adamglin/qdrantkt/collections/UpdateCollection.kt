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
 * Update parameters of the existing collection.
 * @param collectionName Name of the collection to update
 * @param timeout Wait for operation commit timeout in seconds. If timeout is reached - request will return with service error.
 */
suspend fun QdrantClient.updateCollection(
    collectionName: String,
    vectors: VectorsConfigDiff? = null,
    optimizersConfig: OptimizersConfigDiff? = null,
    params: CollectionParamsDiff? = null,
    hnswConfig: HnswConfigDiff? = null,
    quantizationConfig: QuantizationConfigDiff? = null,
    sparseVectors: SparseVectorsConfig? = null,
    strictModeConfig: StrictModeConfig? = null,
    timeout: Int? = null
): Boolean {
    return httpClient.patch("/collections/$collectionName") {
        if (timeout != null) {
            parameter("timeout", timeout)
        }
        setBody(
            UpdateCollectionRequest(
                vectors = vectors,
                optimizersConfig = optimizersConfig,
                params = params,
                hnswConfig = hnswConfig,
                quantizationConfig = quantizationConfig,
                sparseVectors = sparseVectors,
                strictModeConfig = strictModeConfig
            )
        )
    }.qdrantResultOrThrow()
}

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
internal data class UpdateCollectionRequest(
    val vectors: VectorsConfigDiff? = null,
    @SerialName("optimizers_config")
    val optimizersConfig: OptimizersConfigDiff? = null,
    val params: CollectionParamsDiff? = null,
    @SerialName("hnsw_config")
    val hnswConfig: HnswConfigDiff? = null,
    @SerialName("quantization_config")
    val quantizationConfig: QuantizationConfigDiff? = null,
    @SerialName("sparse_vectors")
    val sparseVectors: SparseVectorsConfig? = null,
    @SerialName("strict_mode_config")
    val strictModeConfig: StrictModeConfig? = null
) 