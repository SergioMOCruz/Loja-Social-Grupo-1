package com.grupo1.lojasocial.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo1.lojasocial.data.repository.UserRepository
import com.grupo1.lojasocial.domain.enums.RoleLevel
import com.grupo1.lojasocial.domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val userController: UserRepository = UserRepository()
) : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    init {
        getCurrentUser()
    }

    private fun getCurrentUser() {
        viewModelScope.launch {
            val email = userController.getCurrentUserEmail()
            val user = userController.getUserByEmail(email)
            _currentUser.value = user
        }
    }

    fun getUserRole(): RoleLevel {
        return currentUser.value?.role ?: RoleLevel.VOLUNTEER
    }

    fun registerUser(
        name: String,
        surname: String,
        email: String,
        phone_number: String
    ) {
        val user = mapOf(
            "name" to name,
            "surname" to surname,
            "email" to email,
            "phone_number" to phone_number
        )

        viewModelScope.launch {
            userController.registerUser(user)
        }
    }
}