package com.adamglin.qdrantkt.domain

import kotlinx.serialization.Serializable

@Serializable
data class MultiVectorConfig(
    val comparator: String = "max_sim"
)
