package com.grupo1.lojasocial.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.grupo1.lojasocial.domain.enums.AlertLevel
import com.grupo1.lojasocial.ui.components.utils.profile.ProfileLabel
import com.grupo1.lojasocial.ui.components.utils.profile.WarningLevel
import com.grupo1.lojasocial.ui.screens.header.SubHeaderScreen
import com.grupo1.lojasocial.viewmodel.BeneficiaryViewModel

@Composable
fun ProfileBeneficiaryScreen(
    navController: NavController,
    beneficiaryViewModel: BeneficiaryViewModel
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val profileId = navBackStackEntry?.arguments?.getString("profileId")
    var note by remember { mutableStateOf("") }

    val beneficiaryProfile by beneficiaryViewModel.beneficiaryProfileWithNotes.collectAsState()
    var beneficiary = beneficiaryProfile.beneficiary

    var isEditing by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf(beneficiary.name) }
    var surname by remember { mutableStateOf(beneficiary.surname) }
    var email by remember { mutableStateOf(beneficiary.email) }
    var phoneNumber by remember { mutableStateOf(beneficiary.phoneNumber) }
    var city by remember { mutableStateOf(beneficiary.city) }
    var householdNumber by remember { mutableStateOf(beneficiary.householdNumber) }
    var nationality by remember { mutableStateOf(beneficiary.nationality) }
    var alertLevel by remember { mutableStateOf(AlertLevel.NONE) }
    var notes by remember { mutableStateOf(beneficiaryProfile.notes) }

    LaunchedEffect(key1 = profileId) {
        if (profileId != null) {
            beneficiaryViewModel.getBeneficiaryProfile(profileId)
        }
    }

    LaunchedEffect(beneficiaryProfile) {
        beneficiary = beneficiaryProfile.beneficiary
        name = beneficiary.name
        surname = beneficiary.surname
        email = beneficiary.email
        phoneNumber = beneficiary.phoneNumber
        city = beneficiary.city
        householdNumber = beneficiary.householdNumber
        nationality = beneficiary.nationality
        alertLevel = beneficiary.alertLevel
        notes = beneficiaryProfile.notes
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        SubHeaderScreen(
            title = "Perfil Beneficiário",
            navController
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileLabel(
                label = "Nome Próprio",
                value = name,
                isEditable = isEditing,
                onValueChange = { name = it }
            )
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
                modifier = Modifier.clickable {
                    isEditing = !isEditing
                },
                tint = Color.Black
            )
        }

        ProfileLabel(
            label = "Apelido",
            value = surname,
            isEditable = isEditing,
            onValueChange = { email = it }
        )
        ProfileLabel(
            label = "Email",
            value = email,
            isEditable = isEditing,
            onValueChange = { email = it }
        )
        ProfileLabel(
            label = "Número de Telemóvel",
            value = phoneNumber,
            isEditable = isEditing,
            onValueChange = { phoneNumber = it }
        )
        ProfileLabel(
            label = "Freguesia",
            value = city,
            isEditable = isEditing,
            onValueChange = { city = it }
        )
        ProfileLabel(
            label = "Número de Agregado",
            value = householdNumber,
            isEditable = isEditing,
            onValueChange = { householdNumber = it }
        )
        ProfileLabel(
            label = "Nacionalidade",
            value = nationality,
            isEditable = isEditing,
            onValueChange = { nationality = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Nível de Aviso",
            modifier = Modifier
                .padding(bottom = 8.dp),
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp),
            color = Color.Gray
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            WarningLevel(
                color = Color(0x80FF0000),
                isSelected = alertLevel == AlertLevel.HIGH,
                onClick = {
                    if (isEditing) {
                        alertLevel = AlertLevel.HIGH
                    }
                },
                modifier = Modifier.weight(1f)
            )
            WarningLevel(
                color = Color(0x80FFFF00),
                isSelected = alertLevel == AlertLevel.MEDIUM,
                onClick = {
                    if (isEditing) {
                        alertLevel = AlertLevel.MEDIUM
                    }
                },
                modifier = Modifier.weight(1f)
            )
            WarningLevel(
                color = Color(0x8000FF00),
                isSelected = alertLevel == AlertLevel.LOW,
                onClick = {
                    if (isEditing) {
                        alertLevel = AlertLevel.LOW
                    }
                },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Notas",
            modifier = Modifier
                .padding(bottom = 8.dp),
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp),
            color = Color.Gray
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp,bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                singleLine = true
            )

            IconButton(
                onClick = {
                    notes += note
                    note = ""
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = if(isEditing) Color.Black else Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        notes.forEach { note ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, bottom = 16.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = note,
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = Color.LightGray,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(8.dp)
                )
                if(isEditing) {
                    IconButton(
                        onClick = {
                            notes -= note
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Remove",
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                isEditing = false
                beneficiaryViewModel.updateBeneficiaryProfile(
                    profileId = profileId!!,
                    name = name,
                    surname = surname,
                    email = email,
                    phoneNumber = phoneNumber,
                    householdNumber = householdNumber,
                    city = city,
                    nationality = nationality,
                    alertLevel = alertLevel,
                    notes = notes
                )
            },
            enabled = isEditing,
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isEditing) Color.Black else Color.Gray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp, start = 13.dp, end = 13.dp)
                .height(50.dp)
        ) {
            Text("Atualizar Perfil")
        }

        Spacer(modifier = Modifier.height(4.dp))

        Button(
            onClick = { /* Handle requests click */ },
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp, start = 13.dp, end = 13.dp)
                .height(50.dp)
        ) {
            Text("Pedidos")
        }
    }
}