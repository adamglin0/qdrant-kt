package com.adamglin.qdrantkt

import com.adamglin.qdrantkt.collections.Collections
import com.adamglin.qdrantkt.collections.listAllCollections
import de.infix.testBalloon.framework.testSuite
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import kotlin.test.assertEquals

val listAllCollectionsByMockTestSuite by testSuite {
    test("listAllCollections should return a list of collections") {
        val mockEngine = MockEngine { request ->
            assertEquals("/collections", request.url.encodedPath)
            respond(
                content = """
                {
                    "result": {
                        "collections": [
                            { "name": "test_collection1" },
                            { "name": "test_collection2" }
                        ]
                    },
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
            url = "localhost"
        )

        val response = client.listAllCollections()

        assertEquals("ok", response.status)
        assertEquals(
            expected = Collections(
                collections = listOf(
                    Collections.Collection("test_collection1"),
                    Collections.Collection("test_collection2")
                )
            ),
            actual = response.result,
        )
    }
}
