package com.adamglin.qdrantkt.domain

import de.infix.testBalloon.framework.testSuite
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.test.assertEquals
import kotlin.test.assertTrue

val qdrantResponseTest by testSuite {
    @Serializable
    data class TestResult(val foo: String)

    test("test deserialization of success response") {
        val responseJson = """
            {
                "result": {
                    "foo": "bar"
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
        """.trimIndent()

        val response: QdrantResponse<TestResult> = Json.decodeFromString(responseJson)

        assertTrue(response is QdrantResponse.Success)
        assertEquals("ok", response.status)
        assertEquals(TestResult("bar"), response.result)
    }

    test("test serialization of error response") {
        val responseJson = """
            {
                "status": {
                    "error": "Not found"
                },
                "time": 0.0
            }
        """.trimIndent()

        val response: QdrantResponse<TestResult> = Json.decodeFromString(responseJson)

        assertTrue(response is QdrantResponse.Error)
        assertEquals("Not found", response.status.error)
    }
}
