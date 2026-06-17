package com.example.lenaycarbon.ui.confirmacion.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.lenaycarbon.data.local.entity.TipoEntrega
import com.example.lenaycarbon.ui.theme.AppPrimaryOrange

@Composable
fun SelectDeliveryType(
    actualizarTipoEntrega: (TipoEntrega?) -> Unit,
    tipoEntrega: TipoEntrega?,
    listaTipoEntrega: List<TipoEntrega>
){

    Column() {

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(listaTipoEntrega) { item ->

                Card(
                    modifier = Modifier
                        .width(200.dp)
                        .padding(8.dp),
                    onClick = { actualizarTipoEntrega(item) },
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
                            selected = if(tipoEntrega?.id == null) {
                                item.id == 2}
                            else{
                                tipoEntrega.id == item.id
                            },
                            onClick = {
                                actualizarTipoEntrega(item)
                            },
                            colors = RadioButtonDefaults.colors(selectedColor = AppPrimaryOrange)
                        )
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = item.nombre,
                                fontWeight = FontWeight.Bold
                            )

                            Image(
                                painterResource(id = item.imagen),
                                contentDescription = "Logo",
                                modifier = Modifier.size(80.dp)
                            )

                        }
                    }
                }
            }


        }

        if (tipoEntrega?.id == null){
            InStorePickUp()
        }
    }



    }
