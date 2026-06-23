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
import com.example.lenaycarbon.data.local.entity.TipoEntrega
import com.example.lenaycarbon.data.local.entity.TipoPago
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

    fun actualizarTipoEntrega(tipoEntrega: TipoEntrega?) {
        tipoEntregaSeleccionada = tipoEntrega
    }

    fun actualizarTipoPago(tipoPago: TipoPago?) {
        tipoPagoSeleccionado = tipoPago
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
                if (direccion != null) {
                    lugarEntrega = direccion
                } else {
                    errorUbicacion = "No se encontró una dirección"
                }
            }
        } else {
            viewModelScope.launch {
                @Suppress("DEPRECATION") val addresses = geocoder.getFromLocation(lat, lng, 1)
                obteniendoUbicacion = false
                val direccion = addresses?.firstOrNull()?.getAddressLine(0)
                if (direccion != null) {
                    lugarEntrega = direccion
                } else {
                    errorUbicacion = "No se encontró una dirección"
                }
            }
        }
    }
}