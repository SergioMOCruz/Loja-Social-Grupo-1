package com.grupo1.lojasocial.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo1.lojasocial.data.repository.BeneficiaryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BeneficiaryViewModel(
    private val beneficiaryRepository: BeneficiaryRepository = BeneficiaryRepository()
) : ViewModel() {

    private val _registrationNotes = MutableStateFlow<List<String>>(emptyList())
    val registrationNotes: StateFlow<List<String>> = _registrationNotes

    fun addNoteOnRegistration(note: String) {
        _registrationNotes.value += note
    }

    fun registerBeneficiary(
        name: String,
        email: String,
        phoneNumber: String,
        householdNumber: String,
        city: String,
        nationality: String,
        notes: List<String>
    ) {
        viewModelScope.launch {
            val newBeneficiaryData = mapOf(
                "name" to name,
                "email" to email,
                "phone_number" to phoneNumber,
                "household_number" to householdNumber,
                "city" to city,
                "nationality" to nationality,
                "notes" to notes
            )

            beneficiaryRepository.registerBeneficiary(newBeneficiaryData)

            clearRegistrationNotes()
        }
    }

    private fun clearRegistrationNotes() {
        _registrationNotes.value = emptyList()
    }
}