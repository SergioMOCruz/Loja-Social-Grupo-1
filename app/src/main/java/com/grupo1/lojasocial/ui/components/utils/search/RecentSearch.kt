package com.grupo1.lojasocial.ui.components.utils.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.grupo1.lojasocial.domain.model.Beneficiary
import com.grupo1.lojasocial.navigation.Screen

@Composable
fun RecentSearchesList(
    navController: NavController,
    recentSearches: List<Beneficiary>,
    onRemoveClick: (Beneficiary) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
    ) {
        Text(
            text = "Recentes",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(recentSearches) { recentSearch ->
                RecentSearchItem(
                    recentSearch = recentSearch,
                    onClick = {
                        navController.navigate("${Screen.ProfileBeneficiary.route}/${recentSearch.id}")
                    },
                    onRemoveClick = { onRemoveClick(recentSearch) }
                )
            }
        }
    }
}

@Composable
fun RecentSearchItem(
    recentSearch: Beneficiary,
    onClick: () -> Unit,
    onRemoveClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = recentSearch.name + " " + recentSearch.surname,
                fontSize = 16.sp
            )
            Text(
                text = recentSearch.phoneNumber + " • " + recentSearch.nationality,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        IconButton(
            onClick = onRemoveClick,
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Remove",
                tint = Color.Gray
            )
        }
    }
}
