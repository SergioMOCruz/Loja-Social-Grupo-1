package com.grupo1.lojasocial.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.grupo1.lojasocial.domain.model.Session
import kotlinx.coroutines.tasks.await

class SessionsRepository {
    private val db = FirebaseFirestore.getInstance()

    private val sessionsCollection = db.collection("sessions")

    suspend fun getOpenSessions(): List<Session> {
        return try {
            val documents = sessionsCollection
                .whereEqualTo("exitTime", null)
                .orderBy("enterTime", Query.Direction.DESCENDING)
                .get()
                .await()

            Log.d("SessionsRepository", "Documents: $documents")
            documents.toObjects(Session::class.java).mapIndexed { index, session ->
                session.copy(id = documents.documents[index].id)
            }
        } catch (e: Exception) {
            Log.e("SessionsRepository", "Error fetching sessions", e)
            emptyList()
        }
    }

    suspend fun getCloseSessions(): List<Session> {
        return try {
            val documents = sessionsCollection
                .whereNotEqualTo("exitTime", null)
                .orderBy("exitTime", Query.Direction.DESCENDING)
                .get()
                .await()

            documents.toObjects(Session::class.java).mapIndexed { index, session ->
                session.copy(id = documents.documents[index].id)
            }

        } catch (e: Exception) {
            Log.e("SessionsRepository", "Error fetching sessions", e)
            emptyList()
        }
    }
}
