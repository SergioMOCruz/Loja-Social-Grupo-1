package com.grupo1.lojasocial.ui.screens.schedule

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.grupo1.lojasocial.ui.screens.header.SubHeaderScreen
import com.grupo1.lojasocial.viewmodel.ScheduleViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScheduleScreen(
    navController: NavController,
    scheduleViewModel: ScheduleViewModel
) {
    var date by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // DatePickerDialog logic
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val formattedDate = "$year-${(month + 1).toString().padStart(2, '0')}-${dayOfMonth.toString().padStart(2, '0')}"
                date = formattedDate
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    Scaffold(
        topBar = {
            SubHeaderScreen(
                title = "Criar Novo Horário",
                subtitle = "",
                navController = navController
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Button for Date Picker
            Button(
                onClick = { datePickerDialog.show() },
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
                    text = if (date.isNotEmpty()) date else "Escolher Data",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Start Time Picker Button
            Button(
                onClick = {
                    val startTimePickerDialog = TimePickerDialog(
                        context,
                        { _, hour, minute ->
                            startTime = String.format("%02d:%02d", hour, minute)
                            // Immediately show the End Time Picker after Start Time is selected
                            showEndTimePicker(context, hour, minute, onEndTimeChange = { selectedEndTime ->
                                endTime = selectedEndTime
                            })
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true
                    )
                    startTimePickerDialog.show()
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
                    text = if (startTime.isNotEmpty()) startTime else "Escolher Hora Início",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Display End Time if Start Time is selected
            if (startTime.isNotEmpty()) {

                // End Time Picker Button
                Button(
                    onClick = {
                        showEndTimePicker(context, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), onEndTimeChange = { selectedEndTime ->
                            endTime = selectedEndTime
                        })
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
                        text = if (endTime.isNotEmpty()) endTime else "Escolher Hora Fim",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Create Schedule Button
            Button(
                onClick = {
                    if (date.isNotBlank() && startTime.isNotBlank() && endTime.isNotBlank()) {
                        isLoading = true
                        scheduleViewModel.createSchedule(date, "$startTime-$endTime")
                        isLoading = false
                        navController.popBackStack() // Go back after creation
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(85.dp)
                    .padding(16.dp),
                enabled = date.isNotBlank() && startTime.isNotBlank() && endTime.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Criar Horário", color = Color.White, fontSize = 16.sp)
                }
            }
        }
    }
}

// Function to show End Time Picker
private fun showEndTimePicker(context: android.content.Context, startHour: Int, startMinute: Int, onEndTimeChange: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val endTimePickerDialog = TimePickerDialog(
        context,
        { _, hour, minute ->
            // Ensure the end time is after the start time
            val formattedEndTime = String.format("%02d:%02d", hour, minute)
            onEndTimeChange(formattedEndTime)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )
    endTimePickerDialog.show()
}

