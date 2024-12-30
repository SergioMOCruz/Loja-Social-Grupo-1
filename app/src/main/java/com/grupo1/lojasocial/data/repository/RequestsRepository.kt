package com.grupo1.lojasocial.data.repository

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.grupo1.lojasocial.domain.model.Product
import com.grupo1.lojasocial.domain.model.Requests
import kotlinx.coroutines.tasks.await

class RequestsRepository {
    private val db = FirebaseFirestore.getInstance()
    private val requestsCollection = db.collection("requests")

    suspend fun registerRequest(request: Map<String, Any>): Boolean {
        return try {
            requestsCollection
                .add(request)
                .await()
            true
        } catch (e: Exception) {
            println("Error registering request: ${e.message}")
            false
        }
    }

    suspend fun getRequestsFromBeneficiary(profileId: String): Map<Requests, Map<String, Any>> {
        return try {
            val documentSnapshots = requestsCollection
                .whereEqualTo("id_beneficiary", profileId)
                .get()
                .await()
                .documents

            if (documentSnapshots.isNotEmpty()) {
                documentSnapshots.associate { document ->
                    val data = document.data ?: emptyMap()
                    val idBeneficiary = data["id_beneficiary"] as? String ?: ""
                    val products = data["products"] as? List<Product> ?: emptyList()
                    val date = data["date"] as? Timestamp

                    val request = Requests(
                        id = document.id,
                        id_beneficiary = idBeneficiary,
                        products = products,
                        date = date
                    )

                    request to data
                }
            } else {
                emptyMap()
            }
        } catch (e: Exception) {
            println("Error getting request: ${e.message}")
            emptyMap()
        }
    }

    suspend fun deleteRequest(requestId: String): Boolean {
        return try {
            requestsCollection
                .document(requestId)
                .delete()
                .await()
            true
        } catch (e: Exception) {
            println("Error deleting request: ${e.message}")
            false
        }
    }
}
