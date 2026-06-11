package com.example.lenaycarbon.ui.deliveryType.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lenaycarbon.ui.theme.AppPrimaryOrange

@Composable
fun SetAddres(
    lugarEntrega: String,
    actualizarLugarEntrega: (String) -> Unit
){
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Text(
            text = "Tu pedido será entregado en: ",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 18.sp
        )

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 3.dp)
        )

        Spacer(modifier = Modifier.height(3.dp))

        OutlinedTextField(
            value = lugarEntrega,
            placeholder = {
                Text("Indique la dirección"
                )},
            onValueChange = {actualizarLugarEntrega(it)},
            label = {
                Text(
                    text = "Dirección"
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AppPrimaryOrange,
                unfocusedBorderColor = Color.LightGray
            ),
        )
    }

}