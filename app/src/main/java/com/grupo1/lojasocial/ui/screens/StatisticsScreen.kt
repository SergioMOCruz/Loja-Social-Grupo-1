package com.grupo1.lojasocial.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
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
import com.grupo1.lojasocial.viewmodel.StatisticsViewModel
import com.grupo1.lojasocial.utils.formatDate

@Composable
fun StatisticsScreen(
    statisticsViewModel: StatisticsViewModel,
) {
    var startDate by remember { mutableStateOf<Long?>(null) }
    var endDate by remember { mutableStateOf<Long?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    var householdNumber by remember { mutableStateOf("") }
    val beneficiariesCount by statisticsViewModel.beneficiariesCount.collectAsState()
    val visitsCount by statisticsViewModel.visitsCount.collectAsState()
    val usersInRange by statisticsViewModel.usersInRange.collectAsState()
    val beneficiariesByHouseHoldNumber by statisticsViewModel.beneficiariesByHouseHoldNumber.collectAsState()

    LaunchedEffect(Unit) {
        statisticsViewModel.fetchBeneficiariesCount()
        statisticsViewModel.fetchVisitsCount()
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = householdNumber,
                onValueChange = { householdNumber = it },
                modifier = Modifier
                    .weight(1f)
                    .background(Color.LightGray, RoundedCornerShape(8.dp))
                    .padding(12.dp),
                decorationBox = { innerTextField ->
                    if (householdNumber.isEmpty()) {
                        Text("Digite o número do Household", color = Color.Gray)
                    }
                    innerTextField()
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextButton(onClick = {
                if (householdNumber.isNotEmpty()) {
                    statisticsViewModel.fetchBeneficiariesByHouseHoldNumber(householdNumber)
                }
            }) {
                Text("Procurar")
            }
        }

        StatisticsCard(
            title = "Beneficiários",
            subtitle = "Número total de beneficiários já registrados",
            value = beneficiariesCount.toString(),
            color = Color(0xFFFFA500)
        )

        StatisticsCard(
            title = "Número de Visitas",
            subtitle = "Total de visitas no intervalo selecionado",
            value = visitsCount.toString(),
            color = Color.Green
        )

        StatisticsCard(
            title = "Utilizadores no Intervalo",
            subtitle = "Número total de utilizadores no intervalo selecionado",
            value = usersInRange.size.toString(),
            color = Color.Cyan
        )

        if (beneficiariesByHouseHoldNumber.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Beneficiários encontrados:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LazyColumn {
                items(beneficiariesByHouseHoldNumber) { beneficiary ->
                    Text(
                        text = beneficiary,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )
                }
            }
        } else if (householdNumber.isNotEmpty()) {
            Text(
                text = "Nenhum beneficiário encontrado para este número.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }

    if (showDatePicker) {
        DateRangePickerModal(
            onDateRangeSelected = { dateRange ->
                startDate = dateRange.first
                endDate = dateRange.second
                if (startDate != null && endDate != null) {
                    statisticsViewModel.fetchUsersBetweenDates(startDate!!, endDate!!)
                }
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false }
        )
    }
}

@Composable
fun StatisticsCard(title: String, subtitle: String, value: String, color: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(40.dp)
                    .background(color = color)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = title, style = MaterialTheme.typography.bodyLarge)
                Text(text = subtitle, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }

            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerModal(
    onDateRangeSelected: (Pair<Long?, Long?>) -> Unit,
    onDismiss: () -> Unit
) {
    val dateRangePickerState = rememberDateRangePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateRangeSelected(
                        Pair(
                            dateRangePickerState.selectedStartDateMillis,
                            dateRangePickerState.selectedEndDateMillis
                        )
                    )
                    onDismiss()
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    ) {
        DateRangePicker(
            state = dateRangePickerState,
            title = {
                Text(
                    text = "Selecione o intervalo de datas"
                )
            },
            showModeToggle = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(16.dp)
        )
    }
}


