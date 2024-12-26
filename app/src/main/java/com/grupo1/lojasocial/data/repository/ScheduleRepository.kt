package com.grupo1.lojasocial.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.grupo1.lojasocial.domain.model.Schedule
import com.grupo1.lojasocial.domain.model.User
import kotlinx.coroutines.tasks.await

class ScheduleRepository {
    private val db = FirebaseFirestore.getInstance()
    private val schedulesCollection = db.collection("schedules")

    // Function to get all schedules where the currentUserId is in the schedule's users list
    suspend fun getSchedulesForUser(currentUserId: String): List<Schedule> {
        try {
            // Use Firestore array-contains query to filter schedules that include the currentUserId in the users list
            val snapshot: QuerySnapshot = schedulesCollection
                .whereArrayContains("users", currentUserId) // This filters based on the array of user IDs
                .get()
                .await()

            // Map Firestore documents to Schedule objects
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

            // Return the list of schedules
            return schedules.filterNotNull()
        } catch (e: Exception) {
            // Handle error (could be logging or rethrowing)
            throw Exception("Error fetching schedules: ${e.message}")
        }
    }
}

   /* // Update the availability of a specific schedule for a user
    suspend fun updateScheduleAvailability(userId: String, scheduleId: String, isAvailable: Boolean): Boolean {
        return try {
            // Set the isAvailable field for the user in the userSchedules sub-collection
            val userScheduleRef = schedulesCollection.document(scheduleId)
                .collection("userSchedules")
                .document(userId)

            // Update the availability status for this schedule and user
            userScheduleRef.set(UserSchedule(scheduleId, isAvailable), SetOptions.merge()).await()

            true
        } catch (e: Exception) {
            println("Error updating schedule availability: ${e.message}")
            false
        }
    }
}*/