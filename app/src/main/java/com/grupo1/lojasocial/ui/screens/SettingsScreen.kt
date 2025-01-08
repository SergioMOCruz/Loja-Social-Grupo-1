package com.grupo1.lojasocial.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.grupo1.lojasocial.domain.enums.RoleLevel
import com.grupo1.lojasocial.navigation.Screen
import com.grupo1.lojasocial.ui.components.utils.options.OptionCard
import com.grupo1.lojasocial.viewmodel.AuthViewModel
import com.grupo1.lojasocial.viewmodel.UserViewModel

@Composable
fun SettingsScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel,
    onLogout: () -> Unit
) {
    val userRole by userViewModel.currentUserRole.collectAsState()

    LaunchedEffect(Unit) { userViewModel.getCurrentUser() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Definições",
                        fontSize = 16.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OptionCard(
                    icon = Icons.Filled.DateRange,
                    text = "Marcar Disponibilidade",
                    onClick = {
                        navController.navigate(Screen.RegisterAvailability.route)
                    }
                )
                OptionCard(
                    icon = Icons.Filled.DateRange,
                    text = "Ver Horários",
                    onClick = {
                        navController.navigate(Screen.Schedule.route)
                    },
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Admin Options - Only visible if the user has the admin role
            if (userRole == RoleLevel.ADMIN) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OptionCard(
                        icon = Icons.Filled.DateRange,
                        text = "Inserir Horário",
                        onClick = {
                            navController.navigate(Screen.CreateSchedule.route)
                        }
                    )
                    OptionCard(
                        icon = Icons.Filled.Person,
                        text = "Gerir Voluntários",
                        onClick = {
                            navController.navigate(Screen.ManageVolunteers.route)
                        }
                    )
                }
            }
        }

        // Logout Button at the bottom
        Button(
            onClick = {
                authViewModel.logout()
                onLogout()
            },
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp, start = 13.dp, end = 13.dp)
                .height(50.dp)
        ) {
            Text(text = "Logout", color = Color.DarkGray, fontWeight = FontWeight.Bold)
        }
    }
}
