package com.adamglin.qdrantkt.domain

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class CollectionInfo(
    val status: CollectionStatus,
    @SerialName("optimizer_status")
    val optimizerStatus: OptimizerStatus,
    @SerialName("vectors_count")
    val vectorsCount: Long? = null,
    @SerialName("indexed_vectors_count")
    val indexedVectorsCount: Long? = null,
    @SerialName("points_count")
    val pointsCount: Long? = null,
    @SerialName("segments_count")
    val segmentsCount: Long,
    val config: CollectionConfig,
    @SerialName("payload_schema")
    val payloadSchema: Map<String, PayloadIndexInfo>
)

/**
 * Current state of the collection.
 */
@Serializable
enum class CollectionStatus {
    @SerialName("green")
    /** all good. */
    Green,

    /** optimization is running. */
    @SerialName("yellow")
    Yellow,

    /** optimizations are possible but not triggered. */
    @SerialName("grey")
    Grey,

    /** some operations failed and was not recovered. */
    @SerialName("red")
    Red
}

@Serializable(with = OptimizerStatus.OptimizerStatusSerializer::class)
sealed class OptimizerStatus {
    @Serializable
    @SerialName("ok")
    object Ok : OptimizerStatus()

    @Serializable
    data class Error(val error: String) : OptimizerStatus()

    object OptimizerStatusSerializer : KSerializer<OptimizerStatus> {
        override val descriptor: SerialDescriptor = buildClassSerialDescriptor("OptimizerStatus")

        override fun serialize(encoder: Encoder, value: OptimizerStatus) {
            when (value) {
                is Ok -> encoder.encodeString("ok")
                is Error -> encoder.encodeSerializableValue(Error.serializer(), value)
            }
        }

        override fun deserialize(decoder: Decoder): OptimizerStatus {
            return try {
                // Try to decode as a string first (for "ok" status)
                val stringValue = decoder.decodeString()
                if (stringValue == "ok") Ok else Error(stringValue)
            } catch (e: Exception) {
                // If string decoding fails, try to decode as an Error object
                decoder.decodeSerializableValue(Error.serializer())
            }
        }
    }
}


@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class CollectionConfig(
    val params: CollectionParams,
    @SerialName("hnsw_config")
    val hnswConfig: HnswConfig,
    @SerialName("optimizer_config")
    val optimizerConfig: OptimizersConfig,
    @SerialName("wal_config")
    val walConfig: WalConfig? = null,
    @SerialName("quantization_config")
    val quantizationConfig: QuantizationConfig? = null
)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class CollectionParams(
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
    val onDiskPayload: Boolean? = null
)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class OptimizersConfig(
    @SerialName("deleted_threshold")
    val deletedThreshold: Double? = null,
    @SerialName("vacuum_min_vector_number")
    val vacuumMinVectorNumber: Int? = null,
    @SerialName("default_segment_number")
    val defaultSegmentNumber: Int? = null,
    @SerialName("max_segment_size")
    val maxSegmentSize: Long? = null,
    @SerialName("memmap_threshold")
    val memmapThreshold: Long? = null,
    @SerialName("indexing_threshold")
    val indexingThreshold: Long? = null,
    @SerialName("flush_interval_sec")
    val flushIntervalSec: Long? = null,
    @SerialName("max_optimization_threads")
    val maxOptimizationThreads: Int? = null
)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class WalConfig(
    @SerialName("wal_capacity_mb")
    val walCapacityMb: Long? = null,
    @SerialName("wal_segments_ahead")
    val walSegmentsAhead: Long? = null
)

@Serializable
sealed class QuantizationConfig {
    @Serializable
    @SerialName("scalar")
    data class Scalar(
        val type: String,
        val quantile: Double? = null,
        @SerialName("always_ram")
        val alwaysRam: Boolean? = null
    ) : QuantizationConfig()

    @Serializable
    @SerialName("product")
    data class Product(
        val compression: String,
        @SerialName("always_ram")
        val alwaysRam: Boolean? = null
    ) : QuantizationConfig()

    @Serializable
    @SerialName("binary")
    data class Binary(
        @SerialName("always_ram")
        val alwaysRam: Boolean? = null
    ) : QuantizationConfig()
}

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class PayloadIndexInfo(
    @SerialName("data_type")
    val dataType: DataType,
    val points: Long? = null,
    @SerialName("field_points")
    val fieldPoints: Long? = null
)

// Update models for PATCH operations
typealias VectorsConfigDiff = Map<String, VectorParamsDiff>

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class VectorParamsDiff(
    @SerialName("hnsw_config")
    val hnswConfig: HnswConfigDiff? = null,
    @SerialName("quantization_config")
    val quantizationConfig: QuantizationConfigDiff? = null,
    @SerialName("on_disk")
    val onDisk: Boolean? = null
)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class CollectionParamsDiff(
    @SerialName("replication_factor")
    val replicationFactor: Int? = null,
    @SerialName("write_consistency_factor")
    val writeConsistencyFactor: Int? = null,
    @SerialName("on_disk_payload")
    val onDiskPayload: Boolean? = null,
    @SerialName("read_fan_out_factor")
    val readFanOutFactor: Int? = null
)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class HnswConfigDiff(
    val m: Int? = null,
    @SerialName("ef_construct")
    val efConstruct: Int? = null,
    @SerialName("full_scan_threshold")
    val fullScanThreshold: Int? = null,
    @SerialName("max_indexing_threads")
    val maxIndexingThreads: Int? = null,
    @SerialName("on_disk")
    val onDisk: Boolean? = null,
    @SerialName("payload_m")
    val payloadM: Int? = null
)

@Serializable
sealed class QuantizationConfigDiff {
    @Serializable
    @SerialName("scalar")
    data class Scalar(
        val type: String,
        val quantile: Double? = null,
        @SerialName("always_ram")
        val alwaysRam: Boolean? = null
    ) : QuantizationConfigDiff()

    @Serializable
    @SerialName("product")
    data class Product(
        val compression: String,
        @SerialName("always_ram")
        val alwaysRam: Boolean? = null
    ) : QuantizationConfigDiff()

    @Serializable
    @SerialName("binary")
    data class Binary(
        @SerialName("always_ram")
        val alwaysRam: Boolean? = null
    ) : QuantizationConfigDiff()

    @Serializable
    @SerialName("disabled")
    object Disabled : QuantizationConfigDiff()
}

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class SparseVectorsConfig(
    val map: Map<String, SparseVectorParams> = emptyMap()
)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class SparseVectorParams(
    val index: SparseIndexConfig? = null
)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class SparseIndexConfig(
    @SerialName("full_scan_threshold")
    val fullScanThreshold: Int? = null,
    @SerialName("on_disk")
    val onDisk: Boolean? = null
)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class StrictModeConfig(
    val enabled: Boolean,
    @SerialName("max_query_limit")
    val maxQueryLimit: Long? = null,
    @SerialName("max_timeout")
    val maxTimeout: Long? = null,
    @SerialName("unindexed_filtering_retrieve")
    val unindexedFilteringRetrieve: Boolean? = null,
    @SerialName("unindexed_filtering_update")
    val unindexedFilteringUpdate: Boolean? = null,
    @SerialName("search_max_hnsw_ef")
    val searchMaxHnswEf: Int? = null,
    @SerialName("search_allow_exact")
    val searchAllowExact: Boolean? = null,
    @SerialName("search_max_oversampling")
    val searchMaxOversampling: Double? = null
) 