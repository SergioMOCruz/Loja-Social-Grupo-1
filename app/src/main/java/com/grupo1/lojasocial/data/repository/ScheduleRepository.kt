package com.grupo1.lojasocial.data.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.grupo1.lojasocial.domain.model.Schedule
import com.grupo1.lojasocial.domain.model.User
import kotlinx.coroutines.tasks.await

class ScheduleRepository {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val schedulesCollection: CollectionReference = firestore.collection("schedules")


    suspend fun getSchedulesForUser(currentUserId: String): List<Schedule> {
        try {
            val snapshot = schedulesCollection
                .get()
                .await()

            val schedules = snapshot.documents.mapNotNull { document ->
                val data = document.data
                val users = (data?.get("users") as? List<String>)?.map { userId -> User(id = userId) } ?: emptyList()
                val schedule = Schedule(
                    id = document.id,
                    date = data?.get("date") as? String ?: "",
                    time = data?.get("time") as? String ?: "",
                    users = users
                )
                schedule
            }

            return schedules.filterNotNull()
        } catch (e: Exception) {
            throw Exception("Error fetching schedules: ${e.message}")
        }
    }


    suspend fun updateSchedules(currentUserId: String, updatedSchedules: List<Schedule>) {
        try {
            updatedSchedules.forEach { schedule ->
                val userIds = schedule.users.map { it.id }.toMutableList()
                val isUserChecked = schedule.users.any { it.id == currentUserId }

                if (isUserChecked && !userIds.contains(currentUserId)) {
                    userIds.add(currentUserId)
                } else if (!isUserChecked && userIds.contains(currentUserId)) {
                    userIds.remove(currentUserId)
                }

                schedulesCollection.document(schedule.id)
                    .update("users", userIds)
                    .await()
            }
        } catch (e: Exception) {
            throw Exception("Error updating schedules: ${e.message}")
        }
    }
}