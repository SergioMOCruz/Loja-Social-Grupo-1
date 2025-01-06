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

    private val _beneficiariesCount = MutableStateFlow<Int?>(0)
    val beneficiariesCount: StateFlow<Int?> = _beneficiariesCount

    private val _visitsCount = MutableStateFlow<Int?>(0)
    val visitsCount: StateFlow<Int?> = _visitsCount

    private val _usersInRange = MutableStateFlow<List<String>>(emptyList())
    val usersInRange: StateFlow<List<String>> = _usersInRange

    private val _nationalityNumbers = MutableStateFlow<List<String>>(emptyList())
    val nationalityNumbers: StateFlow<List<String>> = _nationalityNumbers


    fun getBeneficiariesCount() {
        statisticsRepository.getBeneficiariesCount { count ->
            viewModelScope.launch {
                _beneficiariesCount.value = count
            }
        }
    }

    fun getVisitsCount(startDate: Long, endDate: Long) {
        statisticsRepository.getVisitsCount(startDate, endDate) { count ->
            viewModelScope.launch {
                _visitsCount.value = count
            }
        }
    }

    fun getUsersBetweenDates(startDate: Long, endDate: Long) {
        statisticsRepository.getUsersBetweenDates(startDate, endDate) { users ->
            viewModelScope.launch {
                _usersInRange.value = users
            }
        }
    }

    fun getDifferentNationalities() {
        statisticsRepository.getDifferentNationalities { nationatilies ->
            viewModelScope.launch {
                _nationalityNumbers.value = nationatilies
            }
        }
    }
}