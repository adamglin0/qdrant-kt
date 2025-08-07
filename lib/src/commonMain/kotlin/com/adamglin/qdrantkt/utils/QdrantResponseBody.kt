package com.adamglin.qdrantkt.utils

import com.adamglin.qdrantkt.QdrantApiException
import com.adamglin.qdrantkt.domain.QdrantResponse
import io.ktor.client.call.*
import io.ktor.client.statement.*

internal suspend inline fun <reified T> HttpResponse.qdrantBody(): QdrantResponse<T> = body<QdrantResponse<T>>()

internal suspend inline fun <reified T> HttpResponse.qdrantResultOrThrow(): T {
    return when (val qdrantResponse = qdrantBody<T>()) {
        is QdrantResponse.Error -> throw QdrantApiException(qdrantResponse)
        is QdrantResponse.Success<T> -> qdrantResponse.result
    }
}