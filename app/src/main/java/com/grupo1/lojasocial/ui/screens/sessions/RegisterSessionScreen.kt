package com.grupo1.lojasocial.ui.screens.sessions

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.grupo1.lojasocial.domain.enums.AlertLevel
import com.grupo1.lojasocial.ui.components.utils.sessions.InfoCard
import com.grupo1.lojasocial.ui.screens.header.SubHeaderScreen
import com.grupo1.lojasocial.viewmodel.BeneficiaryViewModel
import com.grupo1.lojasocial.viewmodel.SessionsViewModel
import com.grupo1.lojasocial.viewmodel.VisitsViewModel

@Composable
fun RegisterSessionScreen(
    navController: NavController,
    sessionsViewModel: SessionsViewModel,
    beneficiaryViewModel: BeneficiaryViewModel,
    visitsViewModel: VisitsViewModel
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val profileId = navBackStackEntry?.arguments?.getString("profileId")

    LaunchedEffect(key1 = profileId) {
        if (profileId != null) {
            beneficiaryViewModel.getBeneficiaryProfile(profileId)
            visitsViewModel.getVisitsByBeneficiaryId(profileId)
        }
    }

    val beneficiary by beneficiaryViewModel.beneficiaryProfileWithNotes.collectAsState()
    val visits by visitsViewModel.visitsCount.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        SubHeaderScreen(
            title = "Registar Sessão",
            navController = navController
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = beneficiary.beneficiary.name + " " + beneficiary.beneficiary.surname,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = beneficiary.beneficiary.email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "Visita Nº",
                    style = MaterialTheme.typography.labelSmall,
                )
                Text(
                    text = visits.toString(),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            InfoCard(
                label = "Agregado",
                value = beneficiary.beneficiary.householdNumber,
                modifier = Modifier.weight(1f)
            )
            InfoCard(
                label = "Nacionalidade",
                value = beneficiary.beneficiary.nationality,
                modifier = Modifier.weight(2f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        InfoCard(
            label = "Freguesia",
            value = beneficiary.beneficiary.city,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Nível de Aviso",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        LinearProgressIndicator(
            progress = { 1f },
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = if (beneficiary.beneficiary.alertLevel == AlertLevel.HIGH) {
                Color.Red
            } else if (beneficiary.beneficiary.alertLevel == AlertLevel.MEDIUM) {
                Color.Yellow
            } else {
                Color.Green
            },
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Notas",
            style = MaterialTheme.typography.labelLarge
        )
        beneficiary.notes.forEach {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
            ) {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { /* TODO */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color( 0xFF000000),
            )
        ) {
            Text(
                text = "Registar Sessão",
                color = Color.White
            )
        }
    }
}