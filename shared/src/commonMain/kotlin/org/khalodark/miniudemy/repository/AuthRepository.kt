package org.khalodark.miniudemy.repository

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.khalodark.miniudemy.SERVER_PORT

@Serializable
data class LoginRequest(val email: String, val password: String)

@Serializable
data class LoginResponse(val token: String, val userId: String)

/**
 * Simple class for requesting login to local host using post
 */
class AuthRepository(private val client: HttpClient) {

    suspend fun login(email: String, password: String): LoginResponse {
        val response: HttpResponse = client.post("http://localhost:${SERVER_PORT}/login") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequest(email, password))
        }
        val json = Json { ignoreUnknownKeys = true } // prevent problems
        return json.decodeFromString<LoginResponse>(response.bodyAsText())
    }

}