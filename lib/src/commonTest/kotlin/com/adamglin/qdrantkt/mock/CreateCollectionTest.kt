package com.adamglin.qdrantkt.mock

import com.adamglin.qdrantkt.QdrantClient
import com.adamglin.qdrantkt.collections.CreateCollectionRequest
import com.adamglin.qdrantkt.collections.createCollection
import com.adamglin.qdrantkt.domain.Distance
import com.adamglin.qdrantkt.domain.QdrantResponse
import com.adamglin.qdrantkt.domain.VectorParams
import de.infix.testBalloon.framework.testSuite
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.http.content.TextContent
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.assertEquals
import kotlin.test.assertTrue

val createCollectionByMockTestSuite by testSuite {
    test("createCollection should send correct request and parse response") {
        val collectionName = "test_collection"
        val mockEngine = MockEngine { request ->
            assertEquals("/collections/$collectionName", request.url.encodedPath)
            assertEquals(HttpMethod.Put, request.method)

            val requestBody = Json.decodeFromString<CreateCollectionRequest>(
                (request.body as TextContent).text
            )

            assertEquals(128, requestBody.vectors[""]?.size)
            assertEquals(Distance.Cosine, requestBody.vectors[""]?.distance)

            respond(
                content = """
                {
                    "result": true,
                    "status": "ok",
                    "time": 0.0,
                    "usage": {
                        "cpu": 0,
                        "payload_io_read": 0,
                        "payload_io_write": 0,
                        "payload_index_io_read": 0,
                        "payload_index_io_write": 0,
                        "vector_io_read": 0,
                        "vector_io_write": 0
                    }
                }
                """.trimIndent(),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val client = QdrantClient(
            httpClientEngine = mockEngine,
            url = "localhost",
        )

        assertTrue {
            client.createCollection(
                collectionName = collectionName,
                vectors = mapOf("" to VectorParams(size = 128, distance = Distance.Cosine))
            )
        }
    }
}