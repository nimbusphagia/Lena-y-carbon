package com.example.lenaycarbon.ui.deliveryType.components

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
import com.example.lenaycarbon.R
import com.example.lenaycarbon.data.dto.TipoPago
import com.example.lenaycarbon.data.mockup.listaTiposPago
import com.example.lenaycarbon.ui.theme.AppPrimaryOrange



@Composable
fun SelectPaymentType(
    metodoPagoSeleccionado: TipoPago,
    actualizarTipoPago:(TipoPago) -> Unit

) {

    Column() {

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

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Card(
                onClick = {
                    actualizarTipoPago(listaTiposPago[0])
                },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier.weight(1f),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp,
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = metodoPagoSeleccionado.id == 1,
                        onClick = { actualizarTipoPago(listaTiposPago[0]) },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = AppPrimaryOrange
                        )
                    )
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = listaTiposPago[0].nombre,
                            fontWeight = FontWeight.Bold,
                        )

                        Image(
                            painterResource(id = R.drawable.yapeicon),
                            contentDescription = null,
                            modifier = Modifier.size(50.dp)
                        )
                    }

                }

            }

            Card(
                onClick = {
                    actualizarTipoPago(listaTiposPago[1])
                },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier.weight(1f),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp,
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = metodoPagoSeleccionado.id == 2,
                        onClick = { actualizarTipoPago(listaTiposPago[1]) },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = AppPrimaryOrange
                        )
                    )
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = listaTiposPago[1].nombre,
                            fontWeight = FontWeight.Bold,
                        )

                        Image(
                            painterResource(id = R.drawable.efectivoicon),
                            contentDescription = null,
                            modifier = Modifier.size(50.dp)
                        )
                    }

                }

            }

        }

    }
}