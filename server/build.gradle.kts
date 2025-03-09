plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    application
}

group = "org.khalodark.miniudemy"
version = "1.0.0"
application {
    mainClass.set("org.khalodark.miniudemy.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

repositories{
    mavenCentral()
    google()
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.contentNegotiation) // JSON Content Negotiation
    implementation(libs.ktor.serialization.kotlinx.json) // Kotlinx Serialization Support
    implementation(libs.ktor.server.logging)                // Logging
    testImplementation(libs.ktor.server.tests)  // Ktor Test Support
    testImplementation(libs.kotlin.test.junit)  // JUnit Support
    // ✅ Add SLF4J API and Logback implementation
    implementation(libs.logback)
    // Include shared module but exclude AndroidX dependencies
    implementation(project(":shared")) {
        exclude(group = "androidx.lifecycle")
        exclude(group = "androidx.collection")
        exclude(group = "androidx.annotation")
        exclude(group = "androidx.arch.core")
    }
}