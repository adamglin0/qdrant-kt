package com.adamglin.qdrantkt

import com.adamglin.qdrantkt.collections.createCollection
import com.adamglin.qdrantkt.collections.deleteCollection
import com.adamglin.qdrantkt.collections.listAllCollections
import com.adamglin.qdrantkt.domain.Distance
import com.adamglin.qdrantkt.domain.VectorParams
import de.infix.testBalloon.framework.testSuite
import kotlin.test.assertEquals
import kotlin.test.assertTrue

val collectionTest by testSuite {
    val client = QdrantClient(platformHttpClientEngine, "http://localhost:6333")
    test("create a collection") {
        client.createCollection(
            collectionName = "test_collection",
            vectors = mapOf("default" to VectorParams(size = 4, distance = Distance.Dot))
        )
    }

    test("list all collections") {
        val list = client.listAllCollections()
        assertEquals(list.size, 1)
    }

    test("delete a collection") {
        assertTrue { client.deleteCollection("test_collection") }
        val list = client.listAllCollections()
        assertEquals(list.size, 0)
    }
}