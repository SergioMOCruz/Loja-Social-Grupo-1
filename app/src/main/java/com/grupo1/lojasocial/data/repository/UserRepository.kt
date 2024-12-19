package com.grupo1.lojasocial.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.grupo1.lojasocial.domain.model.User
import kotlinx.coroutines.tasks.await

class UserRepository {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private val usersCollection = db.collection("users")

    suspend fun getUserByUid(uid: String?): User? {
        return try {
            val document = usersCollection.document(uid ?: return null).get().await()

            if (document.exists()) {
                val user = document.toObject(User::class.java)?.copy(id = document.id)

                user
            } else {
                null
            }
        } catch (e: Exception) {
            println("Error fetching user: ${e.message}")
            null
        }
    }


    suspend fun getUserByEmail(email: String?): User? {
        return try {
            val querySnapshot = usersCollection
                .whereEqualTo("email", email)
                .get()
                .await()

            if (!querySnapshot.isEmpty) {
                val document = querySnapshot.documents.first()

                val user = document.toObject(User::class.java)?.copy(id = document.id)

                user
            } else {
                null
            }
        } catch (e: Exception) {
            println("Error fetching user: ${e.message}")
            null
        }
    }

    fun getCurrentUserEmail(): String? {
        return auth.currentUser?.email
    }
}