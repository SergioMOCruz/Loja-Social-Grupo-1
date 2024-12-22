package com.grupo1.lojasocial.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo1.lojasocial.data.repository.UserRepository
import com.grupo1.lojasocial.data.repository.VisitsRepository
import com.grupo1.lojasocial.domain.model.Visit
import kotlinx.coroutines.launch

class VisitsViewModel(
    private val visitsController: VisitsRepository = VisitsRepository(),
    private val userController: UserRepository = UserRepository()
) : ViewModel() {

    private val _recentVisits = mutableStateOf<List<Visit>?>(null)
    val recentVisits: State<List<Visit>?> get() = _recentVisits

    fun getLast5Visits() {
        viewModelScope.launch {
            val visits = visitsController.getLast5Visits()

            val visitsWithNames = visits?.map { visit ->
                val user = visit.userId.let { userController.getUserByUid(it) }

                visit.copy(name = (user?.name + " " + user?.surname))
            } ?: emptyList()

            _recentVisits.value = visitsWithNames
        }
    }
}
