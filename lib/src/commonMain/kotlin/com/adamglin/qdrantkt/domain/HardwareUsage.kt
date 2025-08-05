package com.adamglin.qdrantkt.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HardwareUsage(
    val cpu: Int,
    @SerialName("payload_io_read")
    val payloadIoRead: Int,
    @SerialName("payload_io_write")
    val payloadIoWrite: Int,
    @SerialName("payload_index_io_read")
    val payloadIndexIoRead: Int,
    @SerialName("payload_index_io_write")
    val payloadIndexIoWrite: Int,
    @SerialName("vector_io_read")
    val vectorIoRead: Int,
    @SerialName("vector_io_write")
    val vectorIoWrite: Int
)