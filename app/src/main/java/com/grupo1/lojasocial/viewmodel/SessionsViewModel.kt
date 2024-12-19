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
    private val _sessions = MutableStateFlow<List<Session>?>(emptyList())
    val sessions = _sessions.asStateFlow()

    fun getSessions() {
        viewModelScope.launch {
            val sessions = sessionsControler.getSessions()
            _sessions.value = sessions
            Log.d("SessionsViewModel", "Sessions: $sessions")
        }
    }

    fun openSession(sessionId: String) {
        // Handle session opening logic
    }

    fun closeSession(sessionId: String) {
        // Handle session closing logic
    }
}
