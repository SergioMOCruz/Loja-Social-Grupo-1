package com.grupo1.lojasocial.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo1.lojasocial.SocialStore
import com.grupo1.lojasocial.domain.model.Beneficiary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BeneficiaryViewModel : ViewModel() {

    private val beneficiaryDao = SocialStore.database.beneficiaryDao()

    private val _recentSearches = MutableStateFlow<List<Beneficiary>>(emptyList())
    val recentSearches: StateFlow<List<Beneficiary>> = _recentSearches.asStateFlow()

    init {
        viewModelScope.launch {
            beneficiaryDao.getAllBeneficiaries().collectLatest { beneficiaries ->
                _recentSearches.value = beneficiaries
            }
        }
    }

    fun insertBeneficiary(beneficiary: Beneficiary) {
        viewModelScope.launch {
            val foundBeneficiary = beneficiaryDao.getBeneficiaryById(beneficiary.id)
            if (foundBeneficiary != null) {
                beneficiaryDao.update(beneficiary)
            } else {
                beneficiaryDao.insert(beneficiary)
            }
        }
    }

    fun deleteBeneficiary(beneficiary: Beneficiary) {
        viewModelScope.launch {
            beneficiaryDao.delete(beneficiary)
        }
    }
}
