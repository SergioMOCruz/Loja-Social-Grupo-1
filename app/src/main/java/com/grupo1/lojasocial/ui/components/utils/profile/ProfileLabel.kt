package com.grupo1.lojasocial.ui.components.utils.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileLabel(label: String, value: String, isEditable: Boolean, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(vertical = 12.dp)) {
        Text(
            text = label,
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp),
            color = Color.Gray
        )
        if (isEditable) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.padding(top = 8.dp, start = 8.dp),
                textStyle = TextStyle(fontSize = 16.sp),
                singleLine = true
            )
        } else {
            Text(
                text = value,
                modifier = Modifier.padding(top = 8.dp, start = 8.dp),
                style = TextStyle(fontSize = 16.sp),
            )
        }
    }
}