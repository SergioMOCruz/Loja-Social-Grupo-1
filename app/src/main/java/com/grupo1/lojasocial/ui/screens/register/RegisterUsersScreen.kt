package com.grupo1.lojasocial.ui.screens.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.grupo1.lojasocial.ui.components.utils.inputs.GenericInputField
import com.grupo1.lojasocial.ui.components.utils.inputs.PasswordInput
import com.grupo1.lojasocial.ui.screens.header.SubHeaderScreen
import com.grupo1.lojasocial.utils.isValidEmail
import com.grupo1.lojasocial.viewmodel.UserViewModel

@Composable
fun RegisterVolunteerScreen(
    navController: NavController,
    userViewModel: UserViewModel
) {
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        SubHeaderScreen(
            title = "Registar Novo Voluntário",
            subtitle = "Preencha os campos abaixo",
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
        PasswordInput(
            value = password,
            onValueChange = { password = it },
            label = "Password"
        )
        GenericInputField(
            label = "Nº Telemóvel",
            value = phoneNumber,
            onValueChange = { phoneNumber = it }
        )

        Button(
            onClick = {
                userViewModel.registerUser(
                    name = name,
                    surname = surname,
                    email = email,
                    password = password,
                    phoneNumber = phoneNumber
                )

                navController.popBackStack()
            },
            enabled = name.isNotEmpty() && isValidEmail(email)  && email.isNotEmpty() && password.length >= 6,
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp, start = 13.dp, end = 13.dp)
                .height(50.dp)

        ) {
            Text("Registar Voluntário")
        }
    }
}
