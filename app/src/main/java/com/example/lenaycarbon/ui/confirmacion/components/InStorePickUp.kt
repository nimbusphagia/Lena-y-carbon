package com.example.lenaycarbon.ui.confirmacion.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lenaycarbon.ui.theme.AppPrimaryOrange

@Composable
fun InStorePickUp(){

    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Text(
            text = "Recoje tu pedido en: ",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 18.sp
        )

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 3.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp),
            elevation = CardDefaults.cardElevation(6.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )

        )
        {
            Row(

                verticalAlignment = Alignment.CenterVertically
            ){
                RadioButton(
                    selected = true,
                    onClick = {},
                    colors = RadioButtonDefaults.colors(
                        selectedColor = AppPrimaryOrange
                    )
                )

                Column(
                    modifier = Modifier.padding(8.dp)
                )
                {
                    Text(
                        text = "Sucursal Santa Anita",
                        fontWeight = FontWeight.Bold,
                        )
                    Text("Las Violetas 123")
                    Text("Contacto: +51 933 221 984")
                }
            }

        }
    }
}