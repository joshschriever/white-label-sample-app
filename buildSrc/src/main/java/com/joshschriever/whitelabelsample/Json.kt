package com.joshschriever.whitelabelsample

import kotlinx.serialization.json.Json

internal object Json {
    val nonstrict: Json by lazy {
        Json {
            isLenient = true
            ignoreUnknownKeys = true
        }
    }
    val pretty: Json by lazy {
        Json {
            prettyPrint = true
        }
    }
}
