package com.adamglin.qdrantkt.domain

import kotlinx.serialization.SerialName

/**
 * Define read consistency guarantees for the operation.
 */
enum class ReadConsistency(val value: Int) {
    // todo
    @SerialName("majority")
    Majority(0),

    // todo
    @SerialName("quorum")
    Quorum(1),

    // todo
    @SerialName("all")
    All(2);
}