package org.khalodark.miniudemy

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.application
import org.khalodark.miniudemy.repository.AuthRepository
import org.khalodark.miniudemy.ui.LoginScreen
import org.khalodark.miniudemy.viewmodel.AuthViewModel

fun main() {
    val authRepository = AuthRepository()
    val viewModel = AuthViewModel(authRepository)
    application {
        androidx.compose.ui.window.Window(
            onCloseRequest = ::exitApplication,
            title = "Login App"
        ) {
            LoginScreen(viewModel)
        }
    }
}

@Preview
@Composable
fun AppPreview() {
    LoginScreen(AuthViewModel(AuthRepository()))
}

