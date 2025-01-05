package com.grupo1.lojasocial.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo1.lojasocial.data.repository.StatisticsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StatisticsViewModel(
    private val statisticsRepository: StatisticsRepository = StatisticsRepository()
) : ViewModel() {

    private val _beneficiariesCount = MutableStateFlow<Int?>(null)
    val beneficiariesCount: StateFlow<Int?> = _beneficiariesCount

    private val _visitsCount = MutableStateFlow<Int?>(null)
    val visitsCount: StateFlow<Int?> = _visitsCount

    private val _usersInRange = MutableStateFlow<List<String>>(emptyList())
    val usersInRange: StateFlow<List<String>> = _usersInRange

    private val _beneficiariesByHouseHoldNumber = MutableStateFlow(emptyList<String>())
    val beneficiariesByHouseHoldNumber: StateFlow<List<String>> get() = _beneficiariesByHouseHoldNumber


    fun fetchBeneficiariesCount() {
        statisticsRepository.fetchBeneficiariesCount { count ->
            viewModelScope.launch {
                _beneficiariesCount.value = count
            }
        }
    }

    fun fetchVisitsCount() {
        statisticsRepository.fetchVisitsCount { count ->
            viewModelScope.launch {
                _visitsCount.value = count
            }
        }
    }

    fun fetchUsersBetweenDates(startDate: Long, endDate: Long) {
        statisticsRepository.fetchUsersBetweenDates(startDate, endDate) { users ->
            viewModelScope.launch {
                _usersInRange.value = users
            }
        }
    }

    fun fetchBeneficiariesByHouseHoldNumber(houseHoldNumber: String) {
        statisticsRepository.fetchBeneficiariesByHouseHoldNumber(houseHoldNumber) { beneficiaries ->
            viewModelScope.launch {
                _beneficiariesByHouseHoldNumber.value = beneficiaries
            }
        }
    }
}