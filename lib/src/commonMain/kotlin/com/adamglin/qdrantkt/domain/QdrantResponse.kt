package com.adamglin.qdrantkt.domain

import kotlinx.serialization.Serializable

@Serializable
data class QdrantResponse<T>(
    val usage: HardwareUsage,
    val time: Double,
    val status: String,
    val result: T
)