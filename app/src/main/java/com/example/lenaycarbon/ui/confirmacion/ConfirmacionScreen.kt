package com.example.lenaycarbon.ui.confirmacion

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.lenaycarbon.viewmodel.ConfirmacionViewModel
import com.example.lenaycarbon.ui.confirmacion.components.InStorePickUp
import com.example.lenaycarbon.ui.confirmacion.components.SelectDeliveryType
import com.example.lenaycarbon.ui.confirmacion.components.SelectPaymentType
import com.example.lenaycarbon.ui.confirmacion.components.SetAddres
import com.example.lenaycarbon.ui.confirmacion.components.TotalCalculator
import com.example.lenaycarbon.ui.navigation.Routes
import com.example.lenaycarbon.ui.theme.AppPrimaryOrange
import com.example.lenaycarbon.viewmodel.CarritoViewModel
import kotlinx.coroutines.launch

@Composable
fun ConfirmacionScreen(
    nav: NavController,
    confirmacionViewModel: ConfirmacionViewModel,
    carritoViewModel: CarritoViewModel,
) {
    val tiposEntrega by confirmacionViewModel.listaTipoEntrega.collectAsState()
    val tiposPago by confirmacionViewModel.listaTipoPago.collectAsState()

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(confirmacionViewModel.errorUbicacion) {
        confirmacionViewModel.errorUbicacion?.let { mensaje ->
            Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
            confirmacionViewModel.limpiarErrorUbicacion()
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            confirmacionViewModel.obtenerDireccionActual()
        } else {
            Toast.makeText(context, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
        }
    }

    fun verificarPermisoYObtenerUbicacion() {
        val tienePermiso = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (tienePermiso) {
            confirmacionViewModel.obtenerDireccionActual()
        } else {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.height(9.dp))
        Text(
            text = "Confirma tu identidad",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 16.sp
        )
        HorizontalDivider(modifier = Modifier.padding(vertical = 1.dp))

        OutlinedTextField(
            value = confirmacionViewModel.dni,
            onValueChange = { confirmacionViewModel.actualizarDni(it) },
            label = { Text("DNI") },
            placeholder = { Text("Ingresa tu DNI") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(6.dp))

        OutlinedButton(
            onClick = { confirmacionViewModel.validarDni() },
            enabled = !confirmacionViewModel.validandoDni && confirmacionViewModel.dni.length == 8,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (confirmacionViewModel.validandoDni) {
                Text("Validando...")
            } else {
                Text("Validar DNI")
            }
        }

        confirmacionViewModel.errorDni?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error, fontSize = 11.sp)
        }

        confirmacionViewModel.nombreClienteValidado?.let { nombre ->
            Text(
                text = "✓ Cliente: $nombre",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
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
            tipoEntrega = confirmacionViewModel.tipoEntregaSeleccionada,
            actualizarTipoEntrega = { confirmacionViewModel.actualizarTipoEntrega(it) },
            listaTipoEntrega = tiposEntrega
        )

        Spacer(Modifier.height(10.dp))

        when (confirmacionViewModel.tipoEntregaSeleccionada?.id) {

            2 -> InStorePickUp()

            1 -> SetAddres(
                lugarEntrega = confirmacionViewModel.lugarEntrega,
                actualizarLugarEntrega = {
                    confirmacionViewModel.actualizarLugarEntrega(it)
                },
                onUsarUbicacionActual = {
                    verificarPermisoYObtenerUbicacion()
                },
                obteniendoUbicacion = confirmacionViewModel.obteniendoUbicacion
            )
        }

        Spacer(Modifier.height(10.dp))

        SelectPaymentType(
            confirmacionViewModel.tipoPagoSeleccionado, actualizarTipoPago = {
                confirmacionViewModel.actualizarTipoPago(it)
            }, listaTiposPago = tiposPago
        )

        Spacer(Modifier.height(10.dp))

        TotalCalculator(
            subTotal = carritoViewModel.calcularTotal(),
            confirmacionViewModel.tipoEntregaSeleccionada?.precio ?: 0.00
        )
        Spacer(Modifier.height(10.dp))


        Column(
            modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Button(
                onClick = {
                    scope.launch {
                        val costoEnvio =
                            confirmacionViewModel.tipoEntregaSeleccionada?.precio ?: 0.00
                        val subTotal = carritoViewModel.calcularTotal()

                        val pedidoId = confirmacionViewModel.confirmarPedido(subTotal, costoEnvio)

                        if (pedidoId != null) {
                            carritoViewModel.limpiarCarrito()
                            nav.navigate(
                                Routes.seguimientoRoute(
                                    pedidoId.toInt(), subTotal + costoEnvio
                                )
                            )
                        } else {
                            Toast.makeText(
                                context,
                                "Completa el DNI, tipo de entrega y tipo de pago antes de confirmar",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                },
                enabled = confirmacionViewModel.nombreClienteValidado != null,
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
        }
    }

}