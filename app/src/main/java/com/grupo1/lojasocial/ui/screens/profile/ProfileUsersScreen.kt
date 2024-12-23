package com.grupo1.lojasocial.ui.screens.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.grupo1.lojasocial.ui.components.utils.profile.ProfileLabel
import com.grupo1.lojasocial.ui.screens.header.SubHeaderScreen
import com.grupo1.lojasocial.viewmodel.UserViewModel

@Composable
fun ProfileVolunteerScreen(
    navController: NavController,
    userViewModel: UserViewModel
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val profileId = navBackStackEntry?.arguments?.getString("profileId")
    val user by userViewModel.searchedUser.collectAsState()
    val currentUser by userViewModel.currentUser.collectAsState()

    var id by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var isEditing by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(user) {
        id = user?.id ?: ""
        name = user?.name ?: ""
        surname = user?.surname ?: ""
        email = user?.email ?: ""
        phoneNumber = user?.phoneNumber ?: ""
    }

    LaunchedEffect(key1 = profileId) {
        if (profileId != null) {
            userViewModel.getUserById(profileId)
        }
    }

    LaunchedEffect(Unit) { userViewModel.getCurrentUser() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            SubHeaderScreen(
                title = "Perfil Voluntário",
                subtitle = (user?.name ?: "") + " " + (user?.surname ?: ""),
                navController
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
            ) {
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
                    onValueChange = { surname = it }
                )
                ProfileLabel(
                    label = "Email",
                    value = email,
                    isEditable = false,
                    onValueChange = { email = it }
                )
                ProfileLabel(
                    label = "Número de Telemóvel",
                    value = phoneNumber,
                    isEditable = isEditing,
                    onValueChange = { phoneNumber = it }
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
        ) {
            Button(
                onClick = {
                    isEditing = false
                    userViewModel.updateUser(
                        id = id,
                        name = name,
                        surname = surname,
                        email = email,
                        phoneNumber = phoneNumber
                    )
                },
                enabled = isEditing,
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isEditing) Color.Black else Color.Gray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .height(50.dp)
            ) {
                Text("Atualizar Perfil")
            }

            if ((user?.email ?: "") != (currentUser?.email ?: "")) {
                Button(
                    onClick = { showDialog = true },
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Eliminar")
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(text = "Eliminar utilizador " + (user?.name ?: "") + " " + (user?.surname ?: ""))
            },
            text = {
                Text(text = "Tem a certeza que deseja eliminar o utilizador?")
            },
            confirmButton = {
                TextButton(onClick = {
                    userViewModel.deleteUser(id)
                    showDialog = false
                    navController.popBackStack()
                }) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}