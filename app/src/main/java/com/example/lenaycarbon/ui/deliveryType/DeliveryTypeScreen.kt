package com.example.lenaycarbon.ui.deliveryType

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lenaycarbon.data.mockup.listaTiposPago
import com.example.lenaycarbon.data.mockup.listaTipoEntrega
import com.example.lenaycarbon.ui.deliveryType.components.InStorePickUp
import com.example.lenaycarbon.ui.deliveryType.components.SelectDeliveryType
import com.example.lenaycarbon.ui.deliveryType.components.SelectPaymentType
import com.example.lenaycarbon.ui.deliveryType.components.SetAddres
import com.example.lenaycarbon.ui.deliveryType.components.TotalCalculator
import com.example.lenaycarbon.ui.theme.AppPrimaryOrange

@Composable
fun DeliverTypeScreen(nav: NavController, totalCarrito: Double?){

    var tipoEntrega by remember {
        mutableStateOf(listaTipoEntrega[0])
    }
    var lugarEntrega by remember {
        mutableStateOf("")
    }
    var tipoPago by remember {
        mutableStateOf(listaTiposPago[0])
    }

    //Se debe aplicar validación anterior para que no se reciba null
    val totalCarrito: Double = totalCarrito ?: 0.00

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(30.dp))

        Text(
            text = "Seleccione la modalidad de entrega",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 20.sp
        )

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 3.dp)
        )

        Spacer(Modifier.height(15.dp))

        SelectDeliveryType(
            tipoEntrega = tipoEntrega,
            actualizarTipoEntrega = {tipoEntrega = it}
        )

        Spacer(Modifier.height(10.dp))

        when(tipoEntrega.id) {
            1-> InStorePickUp()

            2 -> SetAddres(
                lugarEntrega = lugarEntrega,
                actualizarLugarEntrega = {
                    lugarEntrega = it
                })
        }

        Spacer(Modifier.height(10.dp))

        SelectPaymentType(tipoPago,
            actualizarTipoPago = {
                tipoPago = it
            })

        Spacer(Modifier.height(10.dp))

        TotalCalculator(subTotal = totalCarrito,
            tipoEntrega.precio
        )
        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Button(
                onClick = {
                    // Confirmar pedido
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = AppPrimaryOrange),
            ) {
                Text(
                    text = "Confirmar pedido",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(6.dp)
                )

            }

            OutlinedButton(
                onClick = {
                    nav.popBackStack()
                },
                border = BorderStroke(1.5.dp, AppPrimaryOrange),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = AppPrimaryOrange),
            ) {
                Text(
                    text = "Regresar",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(6.dp)

                )
            }


        }
    }
}
