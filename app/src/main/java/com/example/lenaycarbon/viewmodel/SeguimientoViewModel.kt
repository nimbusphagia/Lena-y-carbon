package com.example.lenaycarbon.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lenaycarbon.data.local.dto.EstadoPedido
import com.example.lenaycarbon.data.local.entity.PedidoEntity
import com.example.lenaycarbon.data.repository.PedidoRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

data class SeguimientoUiState(
    val isLoading: Boolean = true, val pedido: PedidoEntity? = null
)

class SeguimientoViewModel(application: Application) : AndroidViewModel(application) {

    private val pedidoRepository = PedidoRepository(application)

    private val _uiState = MutableStateFlow(SeguimientoUiState())
    val uiState: StateFlow<SeguimientoUiState> = _uiState.asStateFlow()

    fun cargarPedido(pedidoId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            pedidoRepository.obtenerPedidoPorId(pedidoId).collect { pedidoEntity ->
                if (pedidoEntity != null) {
                    _uiState.value = SeguimientoUiState(isLoading = false, pedido = pedidoEntity)

                    val estadoActual = EstadoPedido.valueOf(pedidoEntity.estado)
                    simularAvanceDelRepartidor(pedidoEntity.id, estadoActual)
                }
            }
        }
    }

    private fun simularAvanceDelRepartidor(pedidoId: Int, estadoActual: EstadoPedido) {
        viewModelScope.launch {
            if (estadoActual == EstadoPedido.ENTREGADO || estadoActual == EstadoPedido.CANCELADO) return@launch

            when (estadoActual) {
                EstadoPedido.REGISTRADO -> {
                    delay(Random.nextLong(5000, 10000))
                    pedidoRepository.actualizarEstadoPedido(pedidoId, EstadoPedido.CONFIRMADO.name)
                }

                EstadoPedido.CONFIRMADO -> {
                    delay(Random.nextLong(10000, 15000))
                    pedidoRepository.actualizarEstadoPedido(
                        pedidoId, EstadoPedido.EN_PREPARACION.name
                    )
                }

                EstadoPedido.EN_PREPARACION -> {
                    delay(Random.nextLong(15000, 20000))
                    pedidoRepository.actualizarEstadoPedido(pedidoId, EstadoPedido.EN_REPARTO.name)
                }

                EstadoPedido.EN_REPARTO -> {
                    delay(Random.nextLong(15000, 20000))
                    pedidoRepository.actualizarEstadoPedido(pedidoId, EstadoPedido.ENTREGADO.name)
                }

                else -> {}
            }
        }
    }
}