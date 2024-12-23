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
    private val userRepository: UserRepository = UserRepository()
) : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    private val _searchedUser = MutableStateFlow<User?>(null)
    val searchedUser: StateFlow<User?> = _searchedUser

    private val _currentUserRole = MutableStateFlow(RoleLevel.VOLUNTEER)
    val currentUserRole: StateFlow<RoleLevel> = _currentUserRole

    init {
        getCurrentUser()
    }

    fun getCurrentUser() {
        viewModelScope.launch {
            val email = userRepository.getCurrentUserEmail()
            val user = userRepository.getUserByEmail(email)
            _currentUser.value = user
            _currentUserRole.value = user?.role ?: RoleLevel.VOLUNTEER
        }
    }

    fun getUserById(id: String) {
        viewModelScope.launch {
            _searchedUser.value = userRepository.getUserById(id)
        }
    }

    fun registerUser(
        name: String,
        surname: String,
        email: String,
        password: String,
        phoneNumber: String
    ) {
        val user = mapOf(
            "name" to name,
            "surname" to surname,
            "email" to email,
            "password" to password,
            "phoneNumber" to phoneNumber
        )

        viewModelScope.launch {
            userRepository.registerUser(user)
        }
    }

    fun updateUser(
        id: String,
        name: String,
        surname: String,
        email: String,
        phoneNumber: String
    ) {
        val user = User(
            id = id,
            name = name,
            surname = surname,
            email = email,
            phoneNumber = phoneNumber
        )

        viewModelScope.launch {
            userRepository.updateUser(user)
        }
    }

    fun deleteUser(id: String) {
        viewModelScope.launch {
            userRepository.deleteUser(id)
        }
    }
}