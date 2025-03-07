package org.khalodark.miniudemy.network

import android.os.Build
import androidx.annotation.RequiresApi
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.engine.okhttp.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.time.Duration

const val TIMEOUT: Long = 30_000

@RequiresApi(Build.VERSION_CODES.O)
actual fun provideHttpClient(): HttpClient {
    return HttpClient(OkHttp) {
        engine {
            config {
                followRedirects(true)
                connectTimeout(Duration.ofMillis(TIMEOUT))
            }
        }
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }
}