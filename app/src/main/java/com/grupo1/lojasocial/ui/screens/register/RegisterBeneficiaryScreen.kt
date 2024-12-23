package com.grupo1.lojasocial.ui.screens.register

import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.grupo1.lojasocial.ui.components.utils.inputs.GenericInputField
import com.grupo1.lojasocial.ui.screens.header.SubHeaderScreen
import com.grupo1.lojasocial.utils.isValidEmail
import com.grupo1.lojasocial.viewmodel.BeneficiaryViewModel

@Composable
fun RegisterBeneficiaryScreen(
    navController: NavController,
    beneficiaryViewModel: BeneficiaryViewModel
) {
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var householdNumber by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var nationality by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    val registrationNotes by beneficiaryViewModel.registrationNotes.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        SubHeaderScreen(
            title = "Registar Novo Beneficiário",
            navController
        )

        GenericInputField(
            label = "Nome Próprio",
            value = name,
            onValueChange = { name = it }
        )
        GenericInputField(
            label = "Apelido",
            value = surname,
            onValueChange = { surname = it }
        )
        GenericInputField(
            label = "E-mail",
            value = email,
            onValueChange = { email = it }
        )
        GenericInputField(
            label = "Nº Telemóvel",
            value = phoneNumber,
            onValueChange = { phoneNumber = it }
        )
        GenericInputField(
            label = "Nº Agregado",
            value = householdNumber,
            onValueChange = { householdNumber = it }
        )
        GenericInputField(
            label = "Cidade",
            value = city,
            onValueChange = { city = it }
        )
        GenericInputField(
            label = "Nacionalidade",
            value = nationality,
            onValueChange = { nationality = it }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                label = { Text("Notas") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                singleLine = true
            )

            IconButton(
                onClick = {
                    beneficiaryViewModel.addNoteOnRegistration(note)
                    note = ""
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        registrationNotes.forEach { note ->
            Text(
                text = note,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                beneficiaryViewModel.registerBeneficiary(
                    name = name,
                    surname = surname,
                    email = email,
                    phoneNumber = phoneNumber,
                    householdNumber = householdNumber,
                    city = city,
                    nationality = nationality,
                    notes = registrationNotes
                )

                navController.popBackStack()
            },
            enabled = name.isNotEmpty() && isValidEmail(email)  && (email.isNotEmpty() || phoneNumber.isNotEmpty()) && householdNumber.isNotEmpty(),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp, start = 13.dp, end = 13.dp)
                .height(50.dp)

        ) {
            Text("Registar Beneficiário")
        }
    }
}