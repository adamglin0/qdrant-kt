package com.adamglin.qdrantkt

import com.adamglin.qdrantkt.domain.QdrantResponse

/**
 * Represents an error response from the Qdrant API.
 * @param error The error message.
 * @param time The time in seconds since the epoch when the error occurred.
 */
class QdrantApiException(
    error: String,
    val time: Double
) : Exception(error) {
    constructor(errorResponse: QdrantResponse.Error) : this(errorResponse.status.error, errorResponse.time)
}