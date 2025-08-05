package com.adamglin.qdrantkt.domain

import kotlinx.serialization.SerialName

data class HnswConfig(
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