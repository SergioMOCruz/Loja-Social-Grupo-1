package com.grupo1.lojasocial.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.grupo1.lojasocial.domain.model.Beneficiary
import com.grupo1.lojasocial.domain.model.User
import kotlinx.coroutines.tasks.await

class SearchRepository {
    private val db = FirebaseFirestore.getInstance()
    private val searchCollectionBeneficiaries = db.collection("beneficiaries")
    private val searchCollectionUsers = db.collection("users")

    suspend fun searchBeneficiaries(query: String): List<Beneficiary> {
        if (query.length >= 3) {
            return try {
                val nameResults = searchCollectionBeneficiaries
                    .whereEqualTo("name", query)
                    .limit(20)
                    .get()
                    .await()

                val results = if (nameResults.isEmpty) {
                    searchCollectionBeneficiaries
                        .whereEqualTo("surname", query)
                        .limit(20)
                        .get()
                        .await()
                } else {
                    nameResults
                }

                results.documents.mapNotNull { doc ->
                    doc.toObject(Beneficiary::class.java)?.copy(id = doc.id)
                }
            } catch (e: Exception) {
                println("Error fetching beneficiaries: ${e.message}")
                emptyList()
            }
        }
        return emptyList()
    }

    suspend fun getAllUsers(): List<User> {
        return try {
            searchCollectionUsers
                .limit(20)
                .get()
                .await()
                .documents.mapNotNull { doc ->
                    doc.toObject(User::class.java)?.copy(id = doc.id)
                }
        } catch (e: Exception) {
            println("Error fetching users: ${e.message}")
            emptyList()
        }
    }

    suspend fun searchUsers(query: String): List<User> {
        if (query.length >= 3) {
            return try {
                val nameResults = searchCollectionUsers
                    .whereEqualTo("name", query)
                    .limit(20)
                    .get()
                    .await()

                val results = if (nameResults.isEmpty) {
                    searchCollectionUsers
                        .whereEqualTo("surname", query)
                        .limit(20)
                        .get()
                        .await()
                } else {
                    nameResults
                }

                results.documents.mapNotNull { doc ->
                    doc.toObject(User::class.java)?.copy(id = doc.id)
                }
            } catch (e: Exception) {
                println("Error fetching user: ${e.message}")
                emptyList()
            }
        }
        return emptyList()
    }
}