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
    abstract val status: String

    // Returns true if this response represents a successful operation
    val isSuccess: Boolean
        get() = status == "ok"

    // Returns true if this response represents an error
    val isError: Boolean
        get() = !isSuccess

    @Serializable
    data class Success<T>(
        override val time: Double,
        override val status: String,
        val usage: HardwareUsage,
        val result: T
    ) : QdrantResponse<T>()

    @Serializable
    data class Error(
        override val time: Double,
        override val status: String
    ) : QdrantResponse<Nothing>()
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

        return if (jsonObject["status"]?.jsonPrimitive?.content == "ok") {
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