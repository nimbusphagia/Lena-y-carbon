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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
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

@Composable
fun ConfirmacionScreen(
    nav: NavController,
    confirmacionViewModel: ConfirmacionViewModel,
    carritoViewModel: CarritoViewModel,
) {
    val tiposEntrega by confirmacionViewModel.listaTipoEntrega.collectAsState()
    val tiposPago by confirmacionViewModel.listaTipoPago.collectAsState()

    val context = LocalContext.current

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
        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Button(
                onClick = {
                    val costoEnvio = confirmacionViewModel.tipoEntregaSeleccionada?.precio ?: 0.00
                    val totalReal = carritoViewModel.calcularTotal() + costoEnvio
                    val nuevoPedidoId = (10000..99999).random()
                    carritoViewModel.limpiarCarrito()
                    nav.navigate(Routes.seguimientoRoute(nuevoPedidoId, totalReal))
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
        }
    }
}