package com.adamglin.qdrantkt.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class VectorParams(
    val size: Int,
    val distance: Distance,
    @SerialName("hnsw_config")
    val hnswConfig: HnswConfig? = null,
    @SerialName("quantization_config")
    val quantizationConfig: String? = null,
    @SerialName("on_disk")
    val onDisk: Boolean = false,
    @SerialName("datatype")
    val dataType: DataType? = null,
    @SerialName("multivector_config")
    val multivectorConfig: MultiVectorConfig? = null,
)
