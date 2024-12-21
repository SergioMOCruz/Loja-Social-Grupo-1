package com.grupo1.lojasocial.ui.components.utils.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.grupo1.lojasocial.domain.model.Beneficiary
import com.grupo1.lojasocial.navigation.Screen
import com.grupo1.lojasocial.viewmodel.LocalHistoryViewModel

@Composable
fun ProfileList(
    navController: NavController,
    profiles: List<Beneficiary>,
    localHistoryViewModel: LocalHistoryViewModel
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(profiles) { profile ->
            ProfileItem(
                profile = profile,
                onClick = {
                    localHistoryViewModel.insertBeneficiaryHistory(profile)
                    navController.navigate("${Screen.ProfileBeneficiary.route}/${profile.id}")
                },
            )
        }
    }
}

@Composable
fun ProfileItem(
    profile: Beneficiary,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text(
                text = profile.name,
                fontSize = 16.sp
            )
            Text(
                text = profile.phone_number + " • " + profile.nationality,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Remove",
            tint = Color.Gray
        )
    }
}