package org.khalodark.miniudemy.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.khalodark.miniudemy.repository.AuthRepository

class AuthViewModel(private val repository: AuthRepository) {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    private val viewModelScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    fun login(email: String, password: String) {
        _loginState.value = LoginState.Loading

        viewModelScope.launch {
            repository.login(email, password).collect { result ->
                result.fold(
                    onSuccess = {
                        _loginState.value = LoginState.Success(it.token)
                    },
                    onFailure = {
                        _loginState.value = LoginState.Error(it.message ?: "Unknown error")
                    }
                )
            }
        }
    }
}


sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val token: String) : LoginState()
    data class Error(val error: String) : LoginState()
}