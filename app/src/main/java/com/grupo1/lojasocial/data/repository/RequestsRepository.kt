package com.grupo1.lojasocial.data.repository

import android.util.Log
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

    suspend fun getRequestsFromBeneficiary(profileId: String): List<Requests> {
        return try {
            val documentSnapshots = requestsCollection
                .whereEqualTo("id_beneficiary", profileId)
                .get()
                .await()
                .documents

            if (documentSnapshots.isNotEmpty()) {
                documentSnapshots.mapNotNull { document ->
                    val data = document.data ?: return@mapNotNull null
                    val idBeneficiary = data["id_beneficiary"] as? String ?: return@mapNotNull null
                    val date = data["date"] as? Timestamp ?: return@mapNotNull null

                    val productsList = data["products"] as? List<*>
                    val products = productsList?.mapNotNull { productMap ->
                        if (productMap is Map<*, *>) {
                            val description = productMap["description"] as? String
                            val quantity = productMap["quantity"] as? Long
                            if (description != null && quantity != null) {
                                Product(description, quantity.toInt())
                            } else {
                                Log.d("RequestsRepository", "Invalid product entry: $productMap")
                                null
                            }
                        } else {
                            Log.d("RequestsRepository", "Unexpected productMap type: $productMap")
                            null
                        }
                    } ?: emptyList()

                    Requests(
                        id = document.id,
                        id_beneficiary = idBeneficiary,
                        date = date,
                        products = products
                    )
                }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("RequestsRepository", "Error getting requests: ${e.message}")
            emptyList()
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
