package com.joshschriever.whitelabelsample

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.ResponseException
import io.ktor.client.features.auth.Auth
import io.ktor.client.features.auth.providers.BasicAuthProvider
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.get
import io.ktor.http.ContentType
import kotlinx.coroutines.runBlocking
import io.ktor.client.features.logging.Logger as KtorLogger
import org.gradle.api.logging.Logger as GradleLogger

internal abstract class BasicApi {

    abstract val baseUrl: String
    open val auth: BasicAuthProvider? = null

    inline fun <reified R> get(path: String, logger: GradleLogger): R = useClientBlocking(logger) {
        get(urlString = "$baseUrl/$path")
    }

    inline fun <reified R> getIfExists(path: String, logger: GradleLogger): R? = useClientBlocking(logger) {
        try {
            get<R>(urlString = "$baseUrl/$path")
        } catch (e: ResponseException) {
            when (e.response.status.value) {
                404 -> null
                else -> throw e
            }
        }
    }

    fun <R> useClientBlocking(
        gradleLogger: GradleLogger,
        logBody: Boolean = true,
        block: suspend HttpClient.() -> R
    ): R {
        val log = StringBuilder("\n")
        val customLogger = object : KtorLogger {
            override fun log(message: String) {
                log.appendLine(message)
            }
        }
        val client = HttpClient(OkHttp) {
            install(Logging) {
                logger = customLogger
                level = if (logBody) LogLevel.BODY else LogLevel.INFO
            }
            install(JsonFeature) {
                serializer = KotlinxSerializer(Json.nonstrict)
                acceptContentTypes = listOf(ContentType.Application.Json, ContentType.Text.Plain)
            }
            auth?.let {
                install(Auth) { providers.add(it) }
            }
            expectSuccess = true
        }

        val result = client.use { runBlocking { it.block() } }
        gradleLogger.lifecycle(log.toString())
        return result
    }
}
