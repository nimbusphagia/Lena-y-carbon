package com.example.lenaycarbon.ui.deliveryType.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.lenaycarbon.R
import com.example.lenaycarbon.data.dto.TipoEntrega
import com.example.lenaycarbon.data.mockup.listaTipoEntrega
import com.example.lenaycarbon.ui.theme.AppPrimaryOrange

@Composable
fun SelectDeliveryType(
    actualizarTipoEntrega: (TipoEntrega) -> Unit,
    tipoEntrega: TipoEntrega
){

    var opcionSeleccionada by remember {
        mutableStateOf(tipoEntrega.id)
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Card(
            modifier = Modifier.weight(1f),
            onClick = { actualizarTipoEntrega(listaTipoEntrega[0]) },
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp,
            )

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = tipoEntrega.id == 1,
                    onClick = {
                        actualizarTipoEntrega(listaTipoEntrega[0])
                    },
                    colors = RadioButtonDefaults.colors(selectedColor = AppPrimaryOrange)
                )
                Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Recojo en tienda",
                        fontWeight = FontWeight.Bold
                    )

                    Image(
                        painterResource(id = R.drawable.iconorestuarant),
                        contentDescription = "Logo",
                        modifier = Modifier.size(80.dp)
                    )

                }
            }
        }
            Card(
                modifier = Modifier.weight(1f),
                onClick = { actualizarTipoEntrega(listaTipoEntrega[1]) },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = tipoEntrega.id == 2,
                        onClick = {
                            actualizarTipoEntrega(listaTipoEntrega[1])
                        },
                        colors = RadioButtonDefaults.colors(selectedColor = AppPrimaryOrange)
                    )

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "Delivery",
                            fontWeight = FontWeight.Bold
                        )

                        Image(
                            painterResource(id = R.drawable.deliveryicon),
                            contentDescription = "IconoDelivery",
                            modifier = Modifier.size(80.dp)
                        )
                    }

                }
            }


        }

    }
