package org.khalodark.miniudemy.repository

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import org.khalodark.miniudemy.SERVER_PORT
import org.khalodark.miniudemy.data.LoginRequest
import org.khalodark.miniudemy.data.LoginResponse

/**
 * Simple class for requesting login to local host using post
 */
class AuthRepository(private val client: HttpClient) {

    fun login(email: String, password: String): Flow<Result<LoginResponse>> = flow {
        try {
            val response: HttpResponse = client.post("http://localhost:${SERVER_PORT}/login") {
                contentType(ContentType.Application.Json)
                setBody(LoginRequest(email, password))
            }
            val json = Json { ignoreUnknownKeys = true } // prevent problems
            val loginResponse = json.decodeFromString<LoginResponse>(response.bodyAsText())
            emit(Result.success(loginResponse))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }

    }

}