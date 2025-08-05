package com.adamglin.qdrantkt.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ShardingMethod {
    @SerialName("auto")
    Auto,

    @SerialName("custom")
    Custom;
}