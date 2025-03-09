package org.khalodark.miniudemy

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.khalodark.miniudemy.data.LoginRequest
import org.khalodark.miniudemy.data.LoginResponse
import org.slf4j.event.Level

fun main() {
    val isDevelopment = System.getProperty("io.ktor.development")?.toBoolean() ?: false

    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0"){
        if (isDevelopment) {
           environment.config.tryGetString("ktor.deployment.environment")?.let {
                log.info("🚀 Running in Development Mode")
           }
        }
        configureSerialization()
        configureRouting()
        configureLogging()
    }.start(wait = true)
}

fun Application.configureSerialization(){
    install(ContentNegotiation){
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
}


fun Application.configureRouting(){
    routing{
        post("/login") {
            val contentType = call.request.header(HttpHeaders.ContentType) ?: ""

            val request: LoginRequest? = when {
                "application/json" in contentType -> call.receiveNullable<LoginRequest>()
                "multipart/form-data" in contentType -> {
                    val formParameters = call.receiveParameters()
                    LoginRequest(
                        email = formParameters["email"] ?: "",
                        password = formParameters["password"] ?: ""
                    )
                }
                else -> null
            }
            if (request == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing request body")
                return@post
            }
            if(request.email == "test@example.com" && request.password == "password123"){
                call.respond(HttpStatusCode.OK, LoginResponse("Success", "Welcome back"))
            }else{
                call.respond(HttpStatusCode.Unauthorized, LoginResponse("Error", "Invalid Credintals"))
            }
        }
        get("/"){
            call.respondText("Hello, World !!")
        }
    }
}
fun Application.configureLogging() {
    install(CallLogging) {
        level = Level.INFO
    }
}