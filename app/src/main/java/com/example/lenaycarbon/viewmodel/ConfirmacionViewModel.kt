package com.example.lenaycarbon.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.location.Geocoder
import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lenaycarbon.data.local.dto.EstadoPedido
import com.example.lenaycarbon.data.local.entity.PedidoEntity
import com.example.lenaycarbon.data.local.entity.TipoEntrega
import com.example.lenaycarbon.data.local.entity.TipoPago
import com.example.lenaycarbon.data.remote.RetrofitClient
import com.example.lenaycarbon.data.repository.PedidoRepository
import com.example.lenaycarbon.data.repository.TipoEntregaRepository
import com.example.lenaycarbon.data.repository.TipoPagoRepository
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalCoroutinesApi::class)
class ConfirmacionViewModel(application: Application) : AndroidViewModel(application) {

    private val tipoEntregaRepository = TipoEntregaRepository(application)
    private val tipoPagoRepository = TipoPagoRepository(application)
    private val pedidoRepository = PedidoRepository(application)

    var listaTipoEntrega = tipoEntregaRepository.tiposEntrega.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    var tipoEntregaSeleccionada by mutableStateOf<TipoEntrega?>(null)

    var listaTipoPago = tipoPagoRepository.tiposPago.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    var tipoPagoSeleccionado by mutableStateOf<TipoPago?>(null)

    fun actualizarTipoEntrega(tipoEntrega: TipoEntrega?) {
        tipoEntregaSeleccionada = tipoEntrega
    }

    fun actualizarTipoPago(tipoPago: TipoPago?) {
        tipoPagoSeleccionado = tipoPago
    }

    var lugarEntrega by mutableStateOf("")
        private set
    var obteniendoUbicacion by mutableStateOf(false)
        private set
    var errorUbicacion by mutableStateOf<String?>(null)
        private set

    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(getApplication<Application>())
    }
    private val geocoder by lazy {
        Geocoder(getApplication(), Locale.getDefault())
    }

    fun actualizarLugarEntrega(direccion: String) {
        lugarEntrega = direccion
    }

    fun limpiarErrorUbicacion() {
        errorUbicacion = null
    }

    @SuppressLint("MissingPermission")
    fun obtenerDireccionActual() {
        obteniendoUbicacion = true
        errorUbicacion = null

        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location ->
                if (location == null) {
                    obteniendoUbicacion = false
                    errorUbicacion = "No se pudo obtener la ubicación"
                    return@addOnSuccessListener
                }
                resolverDireccion(location.latitude, location.longitude)
            }.addOnFailureListener {
                obteniendoUbicacion = false
                errorUbicacion = "Error al obtener ubicación"
            }
    }

    private fun resolverDireccion(lat: Double, lng: Double) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocation(lat, lng, 1) { addresses ->
                obteniendoUbicacion = false
                val direccion = addresses.firstOrNull()?.getAddressLine(0)
                if (direccion != null) lugarEntrega = direccion
                else errorUbicacion = "No se encontró una dirección"
            }
        } else {
            viewModelScope.launch {
                @Suppress("DEPRECATION") val addresses = geocoder.getFromLocation(lat, lng, 1)
                obteniendoUbicacion = false
                val direccion = addresses?.firstOrNull()?.getAddressLine(0)
                if (direccion != null) lugarEntrega = direccion
                else errorUbicacion = "No se encontró una dirección"
            }
        }
    }

    var dni by mutableStateOf("")
        private set
    var nombreClienteValidado by mutableStateOf<String?>(null)
        private set
    var validandoDni by mutableStateOf(false)
        private set
    var errorDni by mutableStateOf<String?>(null)
        private set

    fun actualizarDni(valor: String) {
        dni = valor
        nombreClienteValidado = null
    }

    fun validarDni() {
        if (dni.length != 8) {
            errorDni = "El DNI debe tener 8 dígitos"
            return
        }
        validandoDni = true
        errorDni = null
        viewModelScope.launch {
            try {
                val token = "sk_16677.MAwOuxAlGb5m0foaufDYOrvaKLhpHHYj"
                val response = RetrofitClient.reniecService.consultarDni(dni, token)
                nombreClienteValidado =
                    "${response.nombres} ${response.apellidoPaterno} ${response.apellidoMaterno}"
            } catch (e: Exception) {
                errorDni = "No se pudo validar el DNI. Verifica el número."
            } finally {
                validandoDni = false
            }
        }
    }

    suspend fun confirmarPedido(subTotal: Double, costoEnvio: Double): Long? {
        if (nombreClienteValidado == null) {
            errorDni = "Debes validar tu DNI antes de confirmar"
            return null
        }
        val tipoEntrega = tipoEntregaSeleccionada ?: return null
        val tipoPago = tipoPagoSeleccionado ?: return null

        if (tipoEntrega.nombre == "Delivery" && lugarEntrega.isBlank()) {
            errorUbicacion = "Debes indicar una dirección de entrega"
            return null
        }

        val direccionPedido =
            if (tipoEntrega.nombre == "Delivery") lugarEntrega.takeIf { it.isNotBlank() }
            else null

        val pedido = PedidoEntity(
            codigo = (10000..99999).random(),
            dni = dni,
            estado = EstadoPedido.REGISTRADO.name,
            subTotal = subTotal,
            idTipoEntrega = tipoEntrega.id,
            direccion = direccionPedido,
            total = subTotal + costoEnvio,
            observacion = "",
            fechaPedido = System.currentTimeMillis(),
            idTipoPago = tipoPago.id
        )

        return pedidoRepository.insertarPedido(pedido)
    }
}