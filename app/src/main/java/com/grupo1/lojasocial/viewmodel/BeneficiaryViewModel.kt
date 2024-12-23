package com.grupo1.lojasocial.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo1.lojasocial.data.repository.BeneficiaryRepository
import com.grupo1.lojasocial.domain.enums.AlertLevel
import com.grupo1.lojasocial.domain.model.Beneficiary
import com.grupo1.lojasocial.domain.model.BeneficiaryWithNotes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BeneficiaryViewModel(
    private val beneficiaryRepository: BeneficiaryRepository = BeneficiaryRepository(),
    private val localHistoryViewModel: LocalHistoryViewModel = LocalHistoryViewModel()
) : ViewModel() {

    private val _registrationNotes = MutableStateFlow<List<String>>(emptyList())
    val registrationNotes: StateFlow<List<String>> = _registrationNotes

    private val _beneficiaryProfileWithNotes = MutableStateFlow(BeneficiaryWithNotes(beneficiary = Beneficiary(), notes = emptyList()))
    val beneficiaryProfileWithNotes: StateFlow<BeneficiaryWithNotes> = _beneficiaryProfileWithNotes

    fun addNoteOnRegistration(note: String) {
        _registrationNotes.value += note
    }

    fun registerBeneficiary(
        name: String,
        surname: String,
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
                "surname" to surname,
                "email" to email,
                "phoneNumber" to phoneNumber,
                "householdNumber" to householdNumber,
                "city" to city,
                "nationality" to nationality,
                "alertLevel" to AlertLevel.NONE,
                "notes" to notes
            )

            beneficiaryRepository.registerBeneficiary(newBeneficiaryData)

            clearRegistrationNotes()
        }
    }

    fun getBeneficiaryProfile(profileId: String) {
        viewModelScope.launch {
            _beneficiaryProfileWithNotes.value = beneficiaryRepository.getBeneficiaryProfile(profileId)
        }
    }

    fun updateBeneficiaryProfile(
        profileId: String,
        name: String,
        surname: String,
        email: String,
        phoneNumber: String,
        householdNumber: String,
        city: String,
        nationality: String,
        alertLevel: AlertLevel,
        notes: List<String>
    ) {
        viewModelScope.launch {
            val updatedBeneficiaryData = mapOf(
                "name" to name,
                "surname" to surname,
                "email" to email,
                "phoneNumber" to phoneNumber,
                "householdNumber" to householdNumber,
                "city" to city,
                "nationality" to nationality,
                "alertLevel" to alertLevel,
                "notes" to notes
            )

            val beneficiaryHistory = Beneficiary(
                profileId,
                name,
                surname,
                email,
                phoneNumber,
                householdNumber,
                city,
                nationality,
                alertLevel
            )

            localHistoryViewModel.insertBeneficiaryHistory(beneficiaryHistory)

            beneficiaryRepository.updateBeneficiaryProfile(profileId, updatedBeneficiaryData)
        }
    }

    private fun clearRegistrationNotes() {
        _registrationNotes.value = emptyList()
    }
}