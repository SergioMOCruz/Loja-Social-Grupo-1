package com.grupo1.lojasocial.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo1.lojasocial.data.repository.SearchRepository
import com.grupo1.lojasocial.domain.model.Beneficiary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchRepository: SearchRepository = SearchRepository(),
) : ViewModel() {

    private val _searchResults = MutableStateFlow<List<Beneficiary>?>(null)
    val searchResults: StateFlow<List<Beneficiary>?> = _searchResults

    fun searchBeneficiaries(query: String) {
        viewModelScope.launch {
            val results = searchRepository.searchBeneficiaries(query)
            _searchResults.value = results
        }
    }
}