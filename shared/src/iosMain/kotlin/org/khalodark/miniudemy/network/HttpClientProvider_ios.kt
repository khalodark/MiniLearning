package org.khalodark.miniudemy.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

const val TIMEOUT: Long = 30_000

actual fun provideHttpClient(): HttpClient {
    return HttpClient(Darwin) { // ✅ Use Darwin for iOS
        install(HttpTimeout) {
            requestTimeoutMillis = TIMEOUT
            connectTimeoutMillis = TIMEOUT
            socketTimeoutMillis = TIMEOUT
        }
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }
}