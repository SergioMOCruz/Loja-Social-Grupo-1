package com.grupo1.lojasocial.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.grupo1.lojasocial.data.repository.RequestsRepository
import com.grupo1.lojasocial.domain.model.Product
import com.grupo1.lojasocial.domain.model.Requests
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RequestsViewModel(
    private val requestsRepository: RequestsRepository = RequestsRepository()
) : ViewModel() {

    private val _request = MutableStateFlow(emptyList<Requests>())
    val request : StateFlow<List<Requests>> = _request

    fun registerRequest(
        beneficiaryId: String,
        products: List<Product>,
    ) {
        viewModelScope.launch {
            val newRequest = mapOf(
                "beneficiaryId" to beneficiaryId,
                "products" to products,
                "date" to Timestamp.now(),
            )
            requestsRepository.registerRequest(newRequest)
        }
    }

    fun getRequests(profileId: String) {
        viewModelScope.launch {
            val fetchedRequests = requestsRepository.getRequestsFromBeneficiary(profileId)
            _request.value = fetchedRequests
            Log.d("RequestsViewModel", "Fetched Requests: $fetchedRequests")
        }
    }

    fun deleteRequest(requestId: String): Boolean {
        var isDeleted = false
        viewModelScope.launch {
            isDeleted = requestsRepository.deleteRequest(requestId)
        }
        return isDeleted
    }
}