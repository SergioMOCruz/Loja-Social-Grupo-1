package com.grupo1.lojasocial.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.grupo1.lojasocial.navigation.Screen
import com.grupo1.lojasocial.ui.components.utils.search.RecentProfileBar
import com.grupo1.lojasocial.ui.components.utils.sessions.SessionBox
import com.grupo1.lojasocial.viewmodel.SessionsViewModel
import com.grupo1.lojasocial.viewmodel.UserViewModel
import com.grupo1.lojasocial.viewmodel.VisitsViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    visitsViewModel: VisitsViewModel,
    sessionsViewModel: SessionsViewModel
) {
    val currentUser by userViewModel.currentUser.collectAsState()

    LaunchedEffect(Unit) {
        visitsViewModel.getLast5Visits()
        sessionsViewModel.getOpenSessions()
        sessionsViewModel.getCloseSessions()
    }

    val recentVisits = visitsViewModel.recentVisits.collectAsState().value

    val openSessions = sessionsViewModel.openSessions.collectAsState().value
    val countOpenSession = openSessions?.size ?: 0

    val closeSessions = sessionsViewModel.closeSessions.collectAsState().value
    val countCloseSession = closeSessions?.size ?: 0

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Olá,\n${currentUser?.name ?: ""} ${currentUser?.surname ?: ""}",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Sessões",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SessionBox(
                    count = countCloseSession,
                    label = "Sessões Encerradas",
                    icon = Icons.Default.ArrowBack,
                    color = Color(0x1FA3A3A3),
                    iconColor = Color.Red,
                    onClick = {
                        navController.navigate(Screen.ClosedSessionsList.route)
                    }
                )
                SessionBox(
                    count = countOpenSession,
                    label = "Sessões Abertas",
                    icon = Icons.Default.ArrowForward,
                    color = Color(0x1FA3A3A3),
                    iconColor = Color.Green,
                    onClick = {
                        navController.navigate(Screen.OpenSessionsList.route)
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Visitas Recentes",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            recentVisits?.forEach { visit ->
                RecentProfileBar(
                    navController,
                    visit
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}