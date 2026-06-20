package com.example.lenaycarbon.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lenaycarbon.data.dto.EstadoPedido
import com.example.lenaycarbon.data.dto.Pedido
import com.example.lenaycarbon.data.local.entity.PedidoEntity
import com.example.lenaycarbon.data.local.entity.TipoPago
import com.example.lenaycarbon.data.repository.PedidoRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import kotlin.random.Random

data class SeguimientoUiState(
    val isLoading: Boolean = true,
    val pedido: Pedido? = null
)

class SeguimientoViewModel(application: Application) : AndroidViewModel(application) {

    private val pedidoRepository = PedidoRepository(application)

    private val _uiState = MutableStateFlow(SeguimientoUiState())
    val uiState: StateFlow<SeguimientoUiState> = _uiState.asStateFlow()

    fun cargarPedido(pedidoId: Int, totalReal : Double) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            //REPOSITORIO ----> PARA PODER ESCUCHAR LA BD
            pedidoRepository.obtenerPedidoPorId(pedidoId).collect { pedidoEntity ->

                if (pedidoEntity != null) {
                    val estadoReal = EstadoPedido.valueOf(pedidoEntity.estado)

                    // CONVIRTIENDO LA BD A DAO
                    val pedidoReal = Pedido(
                        id = pedidoEntity.id,
                        codigo = pedidoEntity.codigo,
                        idCliente = pedidoEntity.idCliente,
                        estado = estadoReal,
                        subTotal = pedidoEntity.subTotal,
                        idTipoEntrega = pedidoEntity.idTipoEntrega,
                        total = totalReal,
                        observacion = pedidoEntity.observacion,
                        fechaPedido = Date(pedidoEntity.fechaPedido),
                        tipoPago = TipoPago(id = pedidoEntity.idTipoPago, nombre = "Pago Registrado", imagen = 0)
                    )

                    // LO ENVIO ALA PANTALLA
                    _uiState.value = SeguimientoUiState(
                        isLoading = false,
                        pedido = pedidoReal
                    )

                    // INICIA EL TEMPORIZADOR QUE HACE CAMBIAR EL ESTADO DE PEDIDO
                    simularAvanceDelRepartidor(pedidoEntity.id, estadoReal)

                } else {
                    // COMO EL CARRITO AUN NO GUARDA BD, VOY CREANDO UN DATO
                    val pedidoPrueba = PedidoEntity(
                        id = pedidoId,
                        codigo = 99201,
                        idCliente = 1,
                        estado = EstadoPedido.REGISTRADO.name,
                        subTotal = 60.00,
                        idTipoEntrega = 1,
                        total = 72.90,
                        observacion = "Sin mayonesa",
                        fechaPedido = System.currentTimeMillis(),
                        idTipoPago = 1
                    )
                    pedidoRepository.insertarPedido(pedidoPrueba)
                }
            }
        }
    }

    // MOTOR DE SIMULACIÓN DE ESTADOS
    private fun simularAvanceDelRepartidor(pedidoId: Int, estadoActual: EstadoPedido) {
        viewModelScope.launch {
            if(estadoActual== EstadoPedido.ENTREGADO || estadoActual== EstadoPedido.CANCELADO) return@launch

            when (estadoActual) {
                EstadoPedido.REGISTRADO -> {
                    // ESPERA DE 5 A 10 SEG
                    delay(Random.nextLong(5000, 10000))
                    // ACTUALIZA LA BD
                    pedidoRepository.actualizarEstadoPedido(pedidoId, EstadoPedido.CONFIRMADO.name)
                }
                EstadoPedido.CONFIRMADO -> {
                    // ESPERA DE 10 A 15 SEG
                    delay(Random.nextLong(10000, 15000))
                    pedidoRepository.actualizarEstadoPedido(pedidoId, EstadoPedido.EN_PREPARACION.name)
                }
                EstadoPedido.EN_PREPARACION -> {
                    // ESPERA DE 15 A 20 SEG
                    delay(Random.nextLong(15000, 20000))
                    pedidoRepository.actualizarEstadoPedido(pedidoId, EstadoPedido.EN_REPARTO.name)
                }
                EstadoPedido.EN_REPARTO -> {
                    // ESPERA DE 15 A 20 SEG
                    delay(Random.nextLong(15000, 20000))
                    pedidoRepository.actualizarEstadoPedido(pedidoId, EstadoPedido.ENTREGADO.name)
                }
                else -> {
                }
            }
        }
    }
}