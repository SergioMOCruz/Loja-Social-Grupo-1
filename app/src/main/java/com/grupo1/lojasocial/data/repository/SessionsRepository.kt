package com.grupo1.lojasocial.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.grupo1.lojasocial.domain.model.Session
import kotlinx.coroutines.tasks.await

class SessionsRepository {
    private val db = FirebaseFirestore.getInstance()

    private val sessionsCollection = db.collection("sessions")

    suspend fun getSessions(): List<Session> {
        return try {
            val documents = sessionsCollection
                .orderBy("enterTime", Query.Direction.ASCENDING)
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
