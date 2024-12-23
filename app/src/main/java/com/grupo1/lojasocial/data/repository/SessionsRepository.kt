package com.grupo1.lojasocial.data.repository

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.grupo1.lojasocial.domain.model.Session
import kotlinx.coroutines.tasks.await

class SessionsRepository() {
    private val db = FirebaseFirestore.getInstance()

    private val sessionsCollection = db.collection("sessions")

    suspend fun getOpenSessions(): List<Session> {
        return try {
            val documents = sessionsCollection
                .whereEqualTo("exitTime", null)
                .orderBy("enterTime", Query.Direction.DESCENDING)
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

    suspend fun getCloseSessions(): List<Session> {
        return try {
            val documents = sessionsCollection
                .whereLessThan("exitTime", Timestamp(System.currentTimeMillis() / 1000, 0))
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

    suspend fun openSession(session: Session) {
        try {
            val sessionDoc = sessionsCollection.add(session).await()
            sessionDoc.update("id", sessionDoc.id).await()
        } catch (e: Exception) {
            Log.e("SessionsRepository", "Error opening session", e)
        }
    }

    suspend fun closeSession(sessionId: String) {
        try {
            sessionsCollection.document(sessionId).update("exitTime", Timestamp(System.currentTimeMillis() / 1000, 0)).await()
        } catch (e: Exception) {
            Log.e("SessionsRepository", "Error closing session", e)
        }
    }
}