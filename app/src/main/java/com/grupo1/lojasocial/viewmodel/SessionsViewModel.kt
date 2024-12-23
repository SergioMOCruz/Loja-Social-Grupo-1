package com.grupo1.lojasocial.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo1.lojasocial.data.repository.SessionsRepository
import com.grupo1.lojasocial.domain.model.Session
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SessionsViewModel(
    private val sessionsControler: SessionsRepository = SessionsRepository()
) : ViewModel() {
    private val _openSessions = MutableStateFlow<List<Session>?>(emptyList())
    val openSessions = _openSessions.asStateFlow()

    private val _closeSessions = MutableStateFlow<List<Session>?>(emptyList())
    val closeSessions = _closeSessions.asStateFlow()

    fun getOpenSessions() {
        viewModelScope.launch {
            _openSessions.value = sessionsControler.getOpenSessions()
        }
    }

    fun getCloseSessions() {
        viewModelScope.launch {
            _closeSessions.value = sessionsControler.getCloseSessions()
        }
    }

    fun openSession(sessionId: String) {
        // Handle session opening logic
    }

    fun closeSession(sessionId: String) {
        // Handle session closing logic
    }
}
