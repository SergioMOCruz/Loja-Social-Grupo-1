package com.grupo1.lojasocial.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo1.lojasocial.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authController: AuthRepository = AuthRepository()) : ViewModel() {

    private val _loginState = MutableStateFlow<Result<Unit>?>(null)
    val loginState: StateFlow<Result<Unit>?> = _loginState

    private val _isLoggedIn = MutableStateFlow(authController.isLoggedIn())
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = authController.login(email, password)
            _loginState.value = result

            if (result.isSuccess) {
                _isLoggedIn.value = true
            }
        }
    }

    fun logout() {
        authController.logout()
        _isLoggedIn.value = false
    }

    fun resetLoginState() {
        _loginState.value = null
    }
}
