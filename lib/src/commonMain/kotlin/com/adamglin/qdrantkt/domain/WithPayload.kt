package com.adamglin.qdrantkt.domain

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
sealed interface WithPayload {
    @Serializable
    value class WithBoolean(val value: Boolean) : WithPayload

    @Serializable
    @JvmInline
    value class ListString(val list: List<String>) : WithPayload

    @Serializable
    data class Selector(
        val include: List<String>,
        val exclude: List<String>
    ) : WithPayload
}
