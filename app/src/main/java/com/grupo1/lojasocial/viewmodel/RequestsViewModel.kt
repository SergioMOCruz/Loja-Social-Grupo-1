package com.grupo1.lojasocial.viewmodel

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

    private val _request = MutableStateFlow(emptyMap<Requests, Map<String, Any>>())
    val request : StateFlow<Map<Requests, Map<String, Any>>> = _request

    fun registerRequest(
        id_beneficiary: String,
        products: List<Product>,
    ) {
        viewModelScope.launch {
            val newRequest = mapOf(
                "id_beneficiary" to id_beneficiary,
                "products" to products,
                "date" to Timestamp.now(),
            )

            requestsRepository.registerRequest(newRequest)
        }
    }

    fun getRequests(requestId: String) {
        viewModelScope.launch {
            _request.value = requestsRepository.getRequestsFromBeneficiary(requestId)
        }
    }

    private fun deleteRequest(requestId : String) {
        viewModelScope.launch {
            requestsRepository.deleteRequest( requestId )
        }
    }
}