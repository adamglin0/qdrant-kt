package com.adamglin.qdrantkt

import io.ktor.client.engine.cio.CIO

actual val platformHttpClientEngine: io.ktor.client.engine.HttpClientEngine
    get() = CIO.create {  }