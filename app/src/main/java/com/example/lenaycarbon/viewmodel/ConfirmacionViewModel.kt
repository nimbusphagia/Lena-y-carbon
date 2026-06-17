package com.example.lenaycarbon.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lenaycarbon.data.local.entity.TipoEntrega
import com.example.lenaycarbon.data.local.entity.TipoPago
import com.example.lenaycarbon.data.repository.TipoEntregaRepository
import com.example.lenaycarbon.data.repository.TipoPagoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalCoroutinesApi::class)
class ConfirmacionViewModel(application: Application) : AndroidViewModel(application) {

    private val tipoEntregaRepository = TipoEntregaRepository(application)
    private val tipoPagoRepository =  TipoPagoRepository(application)
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

    fun actualizarTipoEntrega(tipoEntrega: TipoEntrega?){
        tipoEntregaSeleccionada = tipoEntrega
    }

    fun actualizarTipoPago(tipoPago: TipoPago?){
        tipoPagoSeleccionado = tipoPago
    }

}