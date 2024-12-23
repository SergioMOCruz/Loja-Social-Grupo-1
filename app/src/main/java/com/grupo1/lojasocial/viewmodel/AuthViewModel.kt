package com.grupo1.lojasocial.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo1.lojasocial.data.repository.AuthRepository
import com.grupo1.lojasocial.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository = AuthRepository(),
    private val userRepository: UserRepository = UserRepository()
) : ViewModel() {

    private val _loginState = MutableStateFlow<Result<Unit>?>(null)
    val loginState: StateFlow<Result<Unit>?> = _loginState

    private val _isLoggedIn = MutableStateFlow(authRepository.isLoggedIn())
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val user = userRepository.getUserByEmail(email)

            if (user != null) {
                val result = authRepository.login(email, password)
                _loginState.value = result

                if (result.isSuccess) {
                    _isLoggedIn.value = true
                }
            }
        }
    }

    fun logout() {
        authRepository.logout()
        _isLoggedIn.value = false
    }

    fun resetLoginState() {
        _loginState.value = null
    }
}
