package com.grupo1.lojasocial.viewmodel

import android.adservices.adid.AdId
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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

    private val _recentVisits = mutableStateOf<List<Visit>?>(null)
    val recentVisits: State<List<Visit>?> get() = _recentVisits

    private val _visitsCount = MutableStateFlow(0)
    val visitsCount: StateFlow<Int> get() = _visitsCount

    fun getLast5Visits() {
        viewModelScope.launch {
            val visits = visitsRepository.getLast5Visits()

            val visitsWithNames = visits?.map { visit ->
                val beneficiary = beneficiaryRepository.getBeneficiaryByUid(visit.beneficiaryId)

                visit.copy(name = (beneficiary?.name + " " + beneficiary?.surname))
            } ?: emptyList()

            _recentVisits.value = visitsWithNames
        }
    }

    fun getVisitsByBeneficiaryId(beneficiaryId: String) {
        viewModelScope.launch {
            val visits = visitsRepository.getVisitsByBeneficiaryId(beneficiaryId)

            _visitsCount.value = visits?.size ?: 0
        }
    }
}
