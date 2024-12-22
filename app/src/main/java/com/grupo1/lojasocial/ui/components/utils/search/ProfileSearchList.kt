package com.grupo1.lojasocial.ui.components.utils.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowRight
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
import com.grupo1.lojasocial.domain.model.Beneficiary

@Composable
fun ProfileList(
    type: String,
    profiles: List<Beneficiary>,
    onClick: (Beneficiary) -> Unit,
    onRemoveClick: (Beneficiary) -> Unit
) {
    if (type == "recent_profile") {
        Text(
            text = "Recentes",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 8.dp)
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(profiles) { profile ->
            ProfileItem(
                profile,
                type,
                onClick,
                onRemoveClick
            )
        }
    }
}

@Composable
fun ProfileItem(
    profile: Beneficiary,
    type: String,
    onClick: (Beneficiary) -> Unit,
    onRemoveClick: (Beneficiary) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(profile) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text(
                text = profile.name + " " + profile.surname,
                fontSize = 16.sp
            )
            Text(
                text = profile.phoneNumber + " • " + profile.nationality,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        IconButton(
            onClick = {
                if (type == "enter_profile") onClick(profile)
                if (type == "recent_profile") onRemoveClick(profile)
            },
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = if (type == "recent_profile") Icons.Default.Close else Icons.Default.KeyboardArrowRight,
                contentDescription = if (type == "recent_profile") "Remove" else "Enter",
                tint = Color.Gray
            )
        }
    }
}
