package com.grupo1.lojasocial.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo1.lojasocial.data.repository.BeneficiaryRepository
import com.grupo1.lojasocial.data.repository.SessionsRepository
import com.grupo1.lojasocial.domain.model.Session
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.google.firebase.Timestamp
import com.grupo1.lojasocial.domain.model.Beneficiary
import kotlinx.coroutines.flow.StateFlow


class SessionsViewModel(
    private val sessionsController: SessionsRepository = SessionsRepository(),
    private val beneficiaryRepository: BeneficiaryRepository = BeneficiaryRepository()
) : ViewModel() {
    private var _openSessions = MutableStateFlow<List<Session>?>(emptyList())
    val openSessions: StateFlow<List<Session>?> = _openSessions

    private val _closeSessions = MutableStateFlow<List<Session>?>(emptyList())
    val closeSessions: StateFlow<List<Session>?> = _closeSessions

    fun getOpenSessions() {
        viewModelScope.launch {
            _openSessions.value = sessionsController.getOpenSessions()
            Log.d("SessionsViewModel", "Open Sessions: ${_openSessions.value}")
        }
    }

    fun getCloseSessions() {
        viewModelScope.launch {
            _closeSessions.value = sessionsController.getCloseSessions()
            Log.d("SessionsViewModel", "Close Sessions: ${_closeSessions.value}")
        }
    }

    fun openSession(beneficiaryId: String) {
        viewModelScope.launch {
            val beneficiary = beneficiaryRepository.getBeneficiaryByUid(beneficiaryId)
            val beneficiaryName = beneficiary?.name + " " + beneficiary?.surname
            val session = Session(
                    id_beneficiary = beneficiaryId,
                    beneficiaryName = beneficiaryName,
                    enterTime = Timestamp.now(),
                    exitTime = null
            )
            sessionsController.openSession(session)
        }
    }

    fun closeSession(sessionId: String) {
        viewModelScope.launch {
            sessionsController.closeSession(sessionId)
        }
    }
}
