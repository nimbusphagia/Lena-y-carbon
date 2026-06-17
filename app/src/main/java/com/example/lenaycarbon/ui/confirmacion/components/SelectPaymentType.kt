package com.example.lenaycarbon.ui.confirmacion.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lenaycarbon.data.local.entity.TipoPago
import com.example.lenaycarbon.ui.theme.AppPrimaryOrange



@Composable
fun SelectPaymentType(
    metodoPagoSeleccionado: TipoPago?,
    actualizarTipoPago:(TipoPago) -> Unit,
    listaTiposPago: List<TipoPago>

) {

    Column {

        Text(
            text = "Seleccione tipo de Pago",

            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 18.sp
        )

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 3.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(listaTiposPago) { item ->
                Card(
                    onClick = {
                        actualizarTipoPago(item)
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier
                        .width(200.dp)
                        .padding(8.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp,
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = metodoPagoSeleccionado?.id == item.id,
                            onClick = { actualizarTipoPago(item) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = AppPrimaryOrange
                            )
                        )
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = item.nombre,
                                fontWeight = FontWeight.Bold,
                            )

                            Image(
                                painterResource(item.imagen),
                                contentDescription = null,
                                modifier = Modifier.size(50.dp)
                            )
                        }

                    }

                }

            }

        }
    }
}