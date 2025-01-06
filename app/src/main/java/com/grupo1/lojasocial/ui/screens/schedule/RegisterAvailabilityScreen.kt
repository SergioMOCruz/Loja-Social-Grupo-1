package com.grupo1.lojasocial.ui.screens.schedule


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.grupo1.lojasocial.domain.model.Schedule
import com.grupo1.lojasocial.domain.model.User
import com.grupo1.lojasocial.ui.screens.header.SubHeaderScreen
import com.grupo1.lojasocial.viewmodel.ScheduleViewModel
import com.grupo1.lojasocial.viewmodel.UserViewModel
@Composable
fun RegisterAvailabilityScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    scheduleViewModel: ScheduleViewModel
) {
    userViewModel.getCurrentUser()
    val currentUser by userViewModel.currentUser.collectAsState()
    val schedules by scheduleViewModel.schedules.collectAsState()

    var selectedSchedules by remember(schedules) {
        mutableStateOf(
            schedules.filter { schedule ->
                schedule.users.any { it.id == currentUser?.id }
            }.map { it.id }.toSet()
        )
    }

    LaunchedEffect(key1 = currentUser?.id) {
        currentUser?.id?.let { scheduleViewModel.loadSchedulesForUser(it) }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        SubHeaderScreen(
            title = "Registar Disponibilidade",
            subtitle = "",
            navController = navController
        )

        Box(modifier = Modifier
            .weight(1f)
            .padding(16.dp)
        ) {
            if (schedules.isEmpty()) {
                Text(
                    text = "Sem horários disponíveis",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(schedules) { schedule ->
                        ScheduleItem(
                            schedule = schedule,
                            currentUserId = currentUser!!.id,
                            isChecked = selectedSchedules.contains(schedule.id),
                            onUserToggle = { isChecked ->
                                selectedSchedules = if (isChecked) {
                                    selectedSchedules + schedule.id
                                } else {
                                    selectedSchedules - schedule.id
                                }
                            }
                        )
                    }
                }
            }
        }

        Button(
            onClick = {
                val updatedSchedules = schedules.map { schedule ->
                    val isChecked = selectedSchedules.contains(schedule.id)
                    val updatedUsers = if (isChecked) {
                        if (schedule.users.none { it.id == currentUser!!.id }) {
                            schedule.users + User(id = currentUser!!.id)
                        } else {
                            schedule.users
                        }
                    } else {
                        schedule.users.filter { it.id != currentUser!!.id }
                    }
                    schedule.copy(users = updatedUsers)
                }
                scheduleViewModel.updateCheckedSchedules(currentUser!!.id, updatedSchedules, selectedSchedules)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(85.dp)
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                "Atualizar Disponibilidade",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun ScheduleItem(
    schedule: Schedule,
    currentUserId: String,
    isChecked: Boolean,
    onUserToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "${schedule.date}   ${schedule.time}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }

        CustomCheckbox(
            checked = isChecked,
            onCheckedChange = onUserToggle
        )
    }
}

@Composable
fun CustomCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .border(
                width = 1.dp,
                color = if (checked) Color.DarkGray else Color.Gray,
                shape = RoundedCornerShape(4.dp)
            )
            .background(
                color = if (checked) Color.DarkGray else Color.Transparent,
                shape = RoundedCornerShape(4.dp)
            )
            .clickable { onCheckedChange(!checked) },
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(Color.DarkGray, RoundedCornerShape(4.dp))
            )
        }
    }
}
