package com.adamglin.qdrantkt.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class DataType {
    /**
     * Vectors are stored as single-precision floating point numbers, 4 bytes.
     */
    @SerialName("float32")
    Float32,

    /**
     * Vectors are stored as half-precision floating point numbers, 2 bytes.
     */
    @SerialName("float16")
    Float16,

    /**
     * Vectors are stored as unsigned 8-bit integers, 1 byte.
     * Expects vector elements to be in range [0, 255].
     */
    @SerialName("uint8")
    UInt8;
}