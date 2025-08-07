package com.adamglin.qdrantkt.domain

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PolymorphicKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable(with = QdrantResponseSerializer::class)
sealed class QdrantResponse<out T> {
    abstract val time: Double

    @Serializable
    data class Success<T>(
        override val time: Double,
        val status: String,
        val usage: HardwareUsage? = null,
        val result: T
    ) : QdrantResponse<T>()

    @Serializable
    data class Error(
        override val time: Double,
        val status: ErrorStatus
    ) : QdrantResponse<Nothing>() {
        @Serializable
        data class ErrorStatus(val error: String)
    }
}

internal class QdrantResponseSerializer<T>(
    private val resultSerializer: KSerializer<T>
) : KSerializer<QdrantResponse<T>> {

    @OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
    override val descriptor: SerialDescriptor =
        buildSerialDescriptor("QdrantResponse", PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: QdrantResponse<T>) {
        when (value) {
            is QdrantResponse.Success -> encoder.encodeSerializableValue(
                QdrantResponse.Success.serializer(resultSerializer),
                value
            )

            is QdrantResponse.Error -> encoder.encodeSerializableValue(
                QdrantResponse.Error.serializer(),
                value
            )
        }
    }

    override fun deserialize(decoder: Decoder): QdrantResponse<T> {
        val jsonInput = decoder as? JsonDecoder ?: error("Can be used only with JSON")
        val jsonObject = jsonInput.decodeJsonElement().jsonObject

        val statusValue = runCatching {
            jsonObject["status"]?.jsonPrimitive?.content
        }.getOrNull()

        return if (statusValue == "ok") {
            jsonInput.json.decodeFromJsonElement(
                QdrantResponse.Success.serializer(resultSerializer),
                jsonObject
            )
        } else {
            jsonInput.json.decodeFromJsonElement(
                QdrantResponse.Error.serializer(),
                jsonObject
            )
        }
    }
}