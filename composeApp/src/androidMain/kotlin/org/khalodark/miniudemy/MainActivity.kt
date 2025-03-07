package org.khalodark.miniudemy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.khalodark.miniudemy.network.provideHttpClient
import org.khalodark.miniudemy.repository.AuthRepository
import org.khalodark.miniudemy.ui.LoginScreen
import org.khalodark.miniudemy.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = AuthRepository()
        val viewModel = AuthViewModel(repository)

        setContent {
            LoginScreen(viewModel) // Using the shared LoginScreen
        }
    }
}