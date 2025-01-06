package com.grupo1.lojasocial.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo1.lojasocial.data.repository.BeneficiaryRepository
import com.grupo1.lojasocial.data.repository.VisitsRepository
import com.grupo1.lojasocial.domain.model.Visit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VisitsViewModel(
    private val visitsRepository: VisitsRepository = VisitsRepository(),
    private val beneficiaryRepository: BeneficiaryRepository = BeneficiaryRepository(),
) : ViewModel() {

    private val _recentVisits = MutableStateFlow<List<Visit>?>(emptyList())
    val recentVisits: StateFlow<List<Visit>?> = _recentVisits

    private val _visitsCount = MutableStateFlow(0)
    val visitsCount: StateFlow<Int> get() = _visitsCount

    fun getLast5Visits() {
        viewModelScope.launch {
            val visits = visitsRepository.getLast5Visits()

            _recentVisits.value = visits
        }
    }

    fun getVisitsByBeneficiaryId(beneficiaryId: String) {
        viewModelScope.launch {
            val visits = visitsRepository.getVisitsByBeneficiaryId(beneficiaryId)

            _visitsCount.value = visits?.size ?: 0
        }
    }

    fun registerVisit(beneficiaryId: String) {
        viewModelScope.launch {
            visitsRepository.registerVisit(beneficiaryId, beneficiaryRepository)
        }
    }
}
