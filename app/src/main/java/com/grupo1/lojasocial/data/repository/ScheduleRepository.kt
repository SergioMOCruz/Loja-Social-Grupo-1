package com.grupo1.lojasocial.data.repository

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.grupo1.lojasocial.domain.model.Schedule
import com.grupo1.lojasocial.domain.model.User
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ScheduleRepository {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val usersCollection: CollectionReference = firestore.collection("users")
    val schedulesCollection: CollectionReference = firestore.collection("schedules")


    suspend fun getSchedules(): List<Schedule> {
        try {
            // Obter a data atual no formato yyyy-MM-dd
            val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

            val snapshot = schedulesCollection
                .whereGreaterThanOrEqualTo("date", today)
                .orderBy("date", com.google.firebase.firestore.Query.Direction.ASCENDING)
                .get()
                .await()

            val schedules = snapshot.documents.mapNotNull { document ->
                val data = document.data

                val userIds = (data?.get("users") as? List<String>) ?: emptyList()
                val users = userIds.mapNotNull { userId ->
                    try {
                        val userSnapshot = usersCollection.document(userId).get().await()
                        val userName = userSnapshot.getString("name") ?: "Unknown"
                        User(id = userId, name = userName)
                    } catch (e: Exception) {
                        null
                    }
                }

                Schedule(
                    id = document.id,
                    date = data?.get("date") as? String ?: "",
                    time = data?.get("time") as? String ?: "",
                    users = users
                )
            }

            return schedules
        } catch (e: Exception) {
            throw Exception("Error fetching schedules: ${e.message}")
        }
    }


    suspend fun getSchedulesForUser(currentUserId: String): List<Schedule> {
        try {
            // Obter a data atual no formato yyyy-MM-dd
            val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

            val snapshot = schedulesCollection
                .whereGreaterThanOrEqualTo("date", today)
                .orderBy("date", com.google.firebase.firestore.Query.Direction.ASCENDING)
                .get()
                .await()

            val schedules = snapshot.documents.mapNotNull { document ->
                val data = document.data
                val users =
                    (data?.get("users") as? List<String>)?.map { userId -> User(id = userId) }
                        ?: emptyList()
                Schedule(
                    id = document.id,
                    date = data?.get("date") as? String ?: "",
                    time = data?.get("time") as? String ?: "",
                    users = users
                )
            }
            return schedules
        } catch (e: Exception) {
            throw Exception("Error fetching schedules: ${e.message}")
        }
    }


    suspend fun updateSchedules(currentUserId: String, updatedSchedules: List<Schedule>, selectedSchedules: Set<String>) {
        try {
            updatedSchedules.forEach { schedule ->
                // Cria uma lista mutável de IDs de usuários
                val userIds = schedule.users.map { it.id }.toMutableList()

                // Se o checkbox está marcado, adiciona o currentUserId, se não estiver já na lista
                if (selectedSchedules.contains(schedule.id) && !userIds.contains(currentUserId)) {
                    userIds.add(currentUserId)
                }
                // Se o checkbox não está marcado, remove o currentUserId da lista, se ele estiver lá
                else if (!selectedSchedules.contains(schedule.id) && userIds.contains(currentUserId)) {
                    userIds.remove(currentUserId)
                }

                // Atualiza a coleção no Firestore
                schedulesCollection.document(schedule.id)
                    .update("users", userIds)
                    .await()
            }
        } catch (e: Exception) {
            throw Exception("Error updating schedules: ${e.message}")
        }
    }

    suspend fun createSchedule(date: String, time: String) {
        try {
            val newSchedule = hashMapOf(
                "date" to date,
                "time" to time,
                "users" to emptyList<String>()
            )

            schedulesCollection.add(newSchedule).await()
        } catch (e: Exception) {
            throw Exception("Error creating schedule: ${e.message}")
        }
    }
}
