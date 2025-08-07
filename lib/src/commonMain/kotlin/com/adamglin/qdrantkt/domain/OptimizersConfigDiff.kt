package com.adamglin.qdrantkt.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OptimizersConfigDiff(
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