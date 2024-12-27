package com.grupo1.lojasocial.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo1.lojasocial.data.repository.ScheduleRepository
import com.grupo1.lojasocial.domain.model.Schedule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ScheduleViewModel : ViewModel() {
    private val scheduleRepository = ScheduleRepository()

    private val _schedules = MutableStateFlow<List<Schedule>>(emptyList())
    val schedules: StateFlow<List<Schedule>> = _schedules

    fun loadSchedulesForUser(currentUserId: String) {
        viewModelScope.launch {
            val fetchedSchedules = scheduleRepository.getSchedulesForUser(currentUserId)
            _schedules.value=fetchedSchedules

        }
    }
    fun updateCheckedSchedules(currentUserId: String, updatedSchedules: List<Schedule>) {
        viewModelScope.launch {
            try {
                scheduleRepository.updateSchedules(currentUserId, updatedSchedules)
                loadSchedulesForUser(currentUserId) // Refresh schedules after update
            } catch (e: Exception) {
                println("Failed to update schedules: ${e.message}")
            }
        }
    }
}