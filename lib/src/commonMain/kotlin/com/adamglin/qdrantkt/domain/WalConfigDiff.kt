package com.adamglin.qdrantkt.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WalConfigDiff(
    @SerialName("wal_capacity_mb")
    val walCapacityMb: Int? = null,
    @SerialName("wal_segments_ahead")
    val walSegmentsAhead: Int? = null
)