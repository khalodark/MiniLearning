package org.khalodark.miniudemy

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import io.ktor.client.HttpClient

val httpClient = HttpClient() // ✅ Ensure this is accessible

@Composable
fun App() {
    MaterialTheme {
        Column {
            Text("KMP App with Ktor!")
        }
    }
}
