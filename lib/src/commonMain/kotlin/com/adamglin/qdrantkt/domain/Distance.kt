package com.adamglin.qdrantkt.domain

import kotlinx.serialization.Serializable

@Serializable
enum class Distance {
    Cosine,
    Euclid,
    Dot,
    Manhattan;
}