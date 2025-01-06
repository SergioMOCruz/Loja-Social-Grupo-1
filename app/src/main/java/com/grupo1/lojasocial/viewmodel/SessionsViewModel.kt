package com.grupo1.lojasocial.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.grupo1.lojasocial.data.repository.BeneficiaryRepository
import com.grupo1.lojasocial.data.repository.SessionsRepository
import com.grupo1.lojasocial.domain.model.Session
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class SessionsViewModel(
    private val sessionsRepository: SessionsRepository = SessionsRepository(),
    private val beneficiaryRepository: BeneficiaryRepository = BeneficiaryRepository()
) : ViewModel() {
    private var _openSessions = MutableStateFlow<List<Session>?>(emptyList())
    val openSessions: StateFlow<List<Session>?> = _openSessions

    private val _closeSessions = MutableStateFlow<List<Session>?>(emptyList())
    val closeSessions: StateFlow<List<Session>?> = _closeSessions

    fun getOpenSessions() {
        viewModelScope.launch {
            _openSessions.value = sessionsRepository.getOpenSessions()
        }
    }

    fun getCloseSessions() {
        viewModelScope.launch {
            _closeSessions.value = sessionsRepository.getCloseSessions()
        }
    }

    fun openSession(beneficiaryId: String) {
        viewModelScope.launch {
            val beneficiary = beneficiaryRepository.getBeneficiaryByUid(beneficiaryId)
            val beneficiaryName = beneficiary?.name + " " + beneficiary?.surname
            val session = Session(
                    beneficiaryId = beneficiaryId,
                    beneficiaryName = beneficiaryName,
                    enterTime = Timestamp.now(),
                    exitTime = null
            )
            sessionsRepository.openSession(session)
        }
    }

    fun closeSession(sessionId: String) {
        viewModelScope.launch {
            sessionsRepository.closeSession(sessionId)
        }
    }
}
