package com.adamglin.qdrantkt

import io.ktor.client.engine.js.Js

actual val platformHttpClientEngine: io.ktor.client.engine.HttpClientEngine
    get() = Js.create {  }