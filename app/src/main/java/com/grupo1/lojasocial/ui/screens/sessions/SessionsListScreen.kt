package com.grupo1.lojasocial.ui.screens.sessions

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.grupo1.lojasocial.ui.components.utils.sessions.SessionItem
import com.grupo1.lojasocial.ui.screens.header.SubHeaderScreen
import com.grupo1.lojasocial.viewmodel.SessionsViewModel

@Composable
fun SessionsListScreen(
    sessionsViewModel: SessionsViewModel,
    navController: NavController,
    type: String
) {

    val openSessions = sessionsViewModel.openSessions.collectAsState().value
    val closeSessions = sessionsViewModel.closeSessions.collectAsState().value

    LaunchedEffect(Unit) {
        if (type == "open") {
            sessionsViewModel.getOpenSessions()
        } else {
            sessionsViewModel.getCloseSessions()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        SubHeaderScreen(
            title = if (type == "open") "Sessões Abertas" else "Sessões Encerradas",
            navController = navController
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (type == "open") {
                if (openSessions != null) {
                    if (openSessions.isEmpty()) {
                        item {
                            Spacer(modifier = Modifier.size(16.dp))
                            Text(
                                text = "Não existem sessões abertas!",
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    } else {
                        openSessions.forEach { session ->
                            item {
                                Log.d("OpenSessions", session.toString())
                                SessionItem(session = session)
                            }
                        }
                    }
                }
            } else {
                if (closeSessions != null) {
                    if (closeSessions.isEmpty()) {
                        item {
                            Spacer(modifier = Modifier.size(16.dp))
                            Text(
                                text = "Não existem sessões encerradas!",
                                style = MaterialTheme.typography.bodyMedium,

                            )
                        }
                    } else {
                        closeSessions.forEach { session ->
                            item {
                                Log.d("ClosedSessions", session.toString())
                                SessionItem(session = session)
                            }
                        }
                    }
                }
            }
        }
    }
}