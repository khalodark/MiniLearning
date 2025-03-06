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

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val viewModelScope =
        CoroutineScope(Dispatchers.Default + SupervisorJob()) // ✅ Use Default Dispatcher

    fun login(email: String, password: String) {
        viewModelScope.launch {
            repository.login(email, password).collect { result ->
                result.fold(
                    onSuccess = {
                        _isLoggedIn.value = true
                        _errorMessage.value = null
                    },
                    onFailure = {
                        _errorMessage.value = it.message
                        _isLoggedIn.value = false
                    }
                )
            }
        }
    }
}