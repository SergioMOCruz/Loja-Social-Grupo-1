package com.grupo1.lojasocial.ui.screens
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.grupo1.lojasocial.ui.components.utils.requests.RequestItem
import com.grupo1.lojasocial.ui.screens.header.SubHeaderScreen
import com.grupo1.lojasocial.viewmodel.RequestsViewModel

@Composable
fun BeneficiaryRequestsScreen(
    navController: NavController,
    requestsViewModel: RequestsViewModel
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val profileId = navBackStackEntry?.arguments?.getString("profileId")
    val beneficiaryName = navBackStackEntry?.arguments?.getString("beneficiaryName")

    val requests by requestsViewModel.request.collectAsState()

    LaunchedEffect(key1 = profileId) {
        if (profileId != null) {
            requestsViewModel.getRequests(profileId)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SubHeaderScreen(
            title = "Pedidos",
            subtitle = "",
            navController
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = beneficiaryName ?: "",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            requests.forEach() { (request, data) ->
                item {
                    RequestItem(
                        request = request,
                    )
                }
            }
        }
    }
}