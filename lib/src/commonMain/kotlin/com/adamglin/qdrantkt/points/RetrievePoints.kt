package com.adamglin.qdrantkt.points

import com.adamglin.qdrantkt.QdrantClient
import com.adamglin.qdrantkt.domain.ReadConsistency
import com.adamglin.qdrantkt.domain.WithPayload
import com.adamglin.qdrantkt.utils.qdrantResultOrThrow
import io.ktor.client.request.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

suspend fun QdrantClient.retrievePoints(
    collectionName: String,
    ids: List<UInt>,
    consistency: ReadConsistency? = null,
    shardKeys: List<UInt>? = null,
    withPayload: WithPayload? = null,
) {
    return httpClient.get("/collections/${collectionName}/points") {
        setBody(
            RetrievePointsRequest(
                ids = ids,
                shardKey = shardKeys,
                withPayload = withPayload,
            )
        )
    }
        .qdrantResultOrThrow<Unit>()
}

@Serializable
internal data class RetrievePointsRequest(
    val ids: List<UInt>,
    @SerialName("shard_key")
    val shardKey: List<UInt>? = null,
    @SerialName("with_payload")
    val withPayload: WithPayload? = null,
)