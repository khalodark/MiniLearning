import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeCompiler)
    id("org.jetbrains.kotlin.native.cocoapods")
    kotlin("plugin.serialization") version "1.9.0"
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    applyDefaultHierarchyTemplate()

    cocoapods {
        version = "1.0.0"
        summary = "Shared module for iOS"
        homepage = "https://github.com/your-repo"
        ios.deploymentTarget = "13.0"
        framework {
            baseName = "shared"
        }
    }

    jvm()

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.ktor.client.core)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material) // ✅ Add Material support
            implementation(libs.compose.ui) // ✅ Required for UI components
        }

        androidMain.dependencies {
//            implementation(libs.ktor.client.core) // Android
//            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.android.okHttp.engine)
            implementation(libs.ktor.client.android)
//            implementation(libs.ktor.client.content.negotiation)
//            implementation(libs.ktor.client.logging)
            implementation(libs.androidx.activity.compose)
//            implementation(libs.ktor.client.timeout)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin) // iOS
            implementation(libs.compose.ui)
        }
        jvmMain.dependencies {
            implementation(libs.ktor.client.cio) // Android
            implementation(libs.compose.desktop)
        }
        wasmJsMain.dependencies {
            implementation(libs.ktor.client.js)
            implementation(libs.compose.ui)
        }
    }
}

android {
    namespace = "org.khalodark.miniudemy.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
