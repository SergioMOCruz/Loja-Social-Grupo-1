package com.grupo1.lojasocial.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grupo1.lojasocial.ui.components.utils.statistics.DateRangePickerModal
import com.grupo1.lojasocial.ui.components.utils.statistics.StatisticsCard
import com.grupo1.lojasocial.utils.formatDate
import com.grupo1.lojasocial.viewmodel.StatisticsViewModel

@Composable
fun StatisticsScreen(
    statisticsViewModel: StatisticsViewModel,
) {
    var startDate by remember { mutableStateOf<Long?>(null) }
    var endDate by remember { mutableStateOf<Long?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    val beneficiariesCount by statisticsViewModel.beneficiariesCount.collectAsState()
    val visitsCount by statisticsViewModel.visitsCount.collectAsState()
    val usersInRange by statisticsViewModel.usersInRange.collectAsState()
    val nationalityNumbers by statisticsViewModel.nationalityNumbers.collectAsState()

    LaunchedEffect(Unit) {
        statisticsViewModel.getBeneficiariesCount()
        statisticsViewModel.getDifferentNationalities()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Estatísticas",
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp),
                modifier = Modifier.weight(3f),
                textAlign = TextAlign.Center
            )

            IconButton(
                onClick = { showDatePicker = true },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Filtrar"
                )
            }
        }

        startDate?.let {
            Text(
                text = "Data inicial: ${formatDate(it)}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        endDate?.let {
            Text(
                text = "Data final: ${formatDate(it)}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Dados dependentes das datas escolhidas", style = MaterialTheme.typography.titleMedium)

        StatisticsCard(
            title = "Número de Visitas",
            subtitle = "Total de visitas no intervalo selecionado",
            value = visitsCount.toString(),
            color = Color.Green
        )

        StatisticsCard(
            title = "Utilizadores Presentes",
            subtitle = "Número total de utilizadores no intervalo selecionado",
            value = usersInRange.size.toString(),
            color = Color.Cyan
        )

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(color = Color.Gray, thickness = 1.dp)
        Spacer(modifier = Modifier.height(16.dp))

        Text("Dados independentes das datas escolhidas", style = MaterialTheme.typography.titleMedium)
        StatisticsCard(
            title = "Beneficiários",
            subtitle = "Número total de beneficiários",
            value = beneficiariesCount.toString(),
            color = Color(0xFFFFA500)
        )

        StatisticsCard(
            title = "Nacionalidades",
            subtitle = "Número total de nacionalidades",
            value = nationalityNumbers.size.toString(),
            color = Color.Red
        )
    }

    if (showDatePicker) {
        DateRangePickerModal(
            onDateRangeSelected = { dateRange ->
                startDate = dateRange.first
                endDate = dateRange.second
                if (startDate != null && endDate != null) {
                    statisticsViewModel.getUsersBetweenDates(startDate!!, endDate!!)
                    statisticsViewModel.getVisitsCount(startDate!!, endDate!!)
                }
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false }
        )
    }
}
