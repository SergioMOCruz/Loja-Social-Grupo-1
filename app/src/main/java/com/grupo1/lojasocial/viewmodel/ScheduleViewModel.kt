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

    // LiveData or StateFlow to hold the schedules for the UI
    private val _schedules = MutableStateFlow<List<Schedule>>(emptyList())
    val schedules: StateFlow<List<Schedule>> = _schedules

    // Function to load schedules
    fun loadSchedulesForUser(currentUserId: String) {
        viewModelScope.launch {
            val fetchedSchedules = scheduleRepository.getSchedulesForUser(currentUserId)
            _schedules.value=fetchedSchedules

        }
    }
}