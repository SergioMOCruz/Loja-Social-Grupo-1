package com.grupo1.lojasocial.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo1.lojasocial.data.repository.ScheduleRepository
import com.grupo1.lojasocial.domain.model.Schedule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class ScheduleUiState(
    val schedules: List<Schedule> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)

class ScheduleViewModel(
    private val scheduleRepository: ScheduleRepository = ScheduleRepository()
) : ViewModel() {

    // UI State for the screen
    private val _uiState = MutableStateFlow(ScheduleUiState())
    val uiState: StateFlow<ScheduleUiState> = _uiState

    // Individual state flows for specific data (now non-nullable)
    private val _schedules = MutableStateFlow<List<Schedule>>(emptyList())
    val schedules = _schedules.asStateFlow()

    private val _userSchedules = MutableStateFlow<List<Schedule>>(emptyList())
    val userSchedules = _userSchedules.asStateFlow()

    fun loadSchedules() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                val schedules = scheduleRepository.getSchedules()
                _schedules.value = schedules
                _uiState.value = _uiState.value.copy(
                    schedules = schedules,
                    isLoading = false
                )
                Log.d("ScheduleViewModel", "Schedules loaded: ${schedules.size}")
            } catch (e: Exception) {
                _schedules.value = emptyList()
                _uiState.value = _uiState.value.copy(
                    errorMessage = e.message,
                    isLoading = false
                )
                Log.e("ScheduleViewModel", "Error loading schedules: ${e.message}")
            }
        }
    }

    fun loadSchedulesForUser(currentUserId: String) {
        viewModelScope.launch {
            try {
                val schedulesList = scheduleRepository.getSchedulesForUser(currentUserId)
                _schedules.value = schedulesList
            } catch (e: Exception) {
                // Handle error, maybe log it
                Log.e("ScheduleViewModel", "Error loading schedules: ${e.message}")
            }
        }
    }

    fun createSchedule(date: String, time: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                scheduleRepository.createSchedule(date, time)
                loadSchedules()
                _uiState.value = _uiState.value.copy(
                    successMessage = "Horário criado com sucesso!",
                    isLoading = false
                )
                Log.d("ScheduleViewModel", "Schedule created successfully")
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = e.message,
                    isLoading = false
                )
                Log.e("ScheduleViewModel", "Error creating schedule: ${e.message}")
            }
        }
    }

    fun updateCheckedSchedules(currentUserId: String, updatedSchedules: List<Schedule>, selectedSchedules: Set<String>) {
        viewModelScope.launch {
            try {
                scheduleRepository.updateSchedules(currentUserId, updatedSchedules, selectedSchedules)
            } catch (e: Exception) {
                println("Failed to update schedules: ${e.message}")
            }
        }
    }

    fun deleteSchedule(scheduleId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                scheduleRepository.schedulesCollection.document(scheduleId).delete().await()
                loadSchedules()
                _uiState.value = _uiState.value.copy(
                    successMessage = "Horário eliminado com sucesso!",
                    isLoading = false
                )
                Log.d("ScheduleViewModel", "Schedule deleted successfully")
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = e.message,
                    isLoading = false
                )
                Log.e("ScheduleViewModel", "Error deleting schedule: ${e.message}")
            }
        }
    }

    fun clearMessages() {
        _uiState.value = _uiState.value.copy(
            errorMessage = null,
            successMessage = null
        )
    }
}