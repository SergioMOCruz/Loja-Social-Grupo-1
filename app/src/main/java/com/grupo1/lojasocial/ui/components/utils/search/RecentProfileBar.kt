package com.grupo1.lojasocial.ui.components.utils.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.grupo1.lojasocial.domain.model.Visit
import com.grupo1.lojasocial.navigation.Screen
import com.grupo1.lojasocial.utils.formatTimestampToDateAndHour

@Composable
fun RecentProfileBar(
    navController: NavController,
    visit: Visit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color(0x1FA3A3A3), shape = RoundedCornerShape(8.dp))
            .padding(12.dp)
            .clickable(onClick = {
                navController.navigate("${Screen.ProfileBeneficiary.route}/${visit.beneficiaryId}")
            }),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = visit.name,
            color = Color(0x99000000),
            style = MaterialTheme.typography.bodyMedium
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = formatTimestampToDateAndHour(visit.date),
                color = Color(0x99000000),
                style = MaterialTheme.typography.bodySmall
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                tint = Color(0x99000000),
                contentDescription = null,
            )
        }
    }
}