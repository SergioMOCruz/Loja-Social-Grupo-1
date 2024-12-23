package com.grupo1.lojasocial.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo1.lojasocial.data.repository.SearchRepository
import com.grupo1.lojasocial.domain.model.Beneficiary
import com.grupo1.lojasocial.domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchRepository: SearchRepository = SearchRepository(),
) : ViewModel() {

    private val _searchResultsBeneficiaries = MutableStateFlow<List<Beneficiary>?>(null)
    val searchResultsBeneficiaries: StateFlow<List<Beneficiary>?> = _searchResultsBeneficiaries

    private val _searchResultsUsers = MutableStateFlow<List<User>?>(null)
    val searchResultsUsers: StateFlow<List<User>?> = _searchResultsUsers

    fun searchBeneficiaries(query: String) {
        viewModelScope.launch {
            val results = searchRepository.searchBeneficiaries(query)
            _searchResultsBeneficiaries.value = results
        }
    }

    fun getAllUsers() {
        viewModelScope.launch {
            val results = searchRepository.getAllUsers()
            _searchResultsUsers.value = results
        }
    }

    fun searchUsers(query: String) {
        viewModelScope.launch {
            val results = searchRepository.searchUsers(query)
            _searchResultsUsers.value = results
        }
    }
}