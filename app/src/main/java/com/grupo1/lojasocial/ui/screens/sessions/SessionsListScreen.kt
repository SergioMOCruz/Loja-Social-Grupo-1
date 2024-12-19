package com.grupo1.lojasocial.ui.screens.sessions

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.grupo1.lojasocial.ui.components.utils.SessionItem
import com.grupo1.lojasocial.viewmodel.SessionsViewModel

@Composable
fun SessionsListScreen(
    sessionsViewModel: SessionsViewModel,
    onBackClick: () -> Unit = {},
) {

    val sessions = sessionsViewModel.sessions.collectAsState().value

    LaunchedEffect(Unit) {
        sessionsViewModel.getSessions()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        TopAppBar(
            backgroundColor = Color(0xFFFFFFFF),
            title = {
                Text(
                    text = "Sessões Abertas",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFF000000)
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color(0xFF000000)
                    )
                }
            }
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            sessions?.forEach { session ->
                item {
                    SessionItem(session = session)
                }
            }
        }
    }
}