package com.adamglin.qdrantkt

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.jvm.JvmInline

@Serializable
sealed interface TestInterface

@Serializable
@JvmInline
value class Test(val value: String) : TestInterface

fun main() {
    println(Json.encodeToString(Test("!23") as TestInterface))
}