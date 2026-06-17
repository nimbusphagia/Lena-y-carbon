package com.example.lenaycarbon.ui.seguimiento

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lenaycarbon.data.dto.EstadoPedido
import com.example.lenaycarbon.data.dto.Pedido
import com.example.lenaycarbon.data.local.entity.TipoPago
//CUANDO CREEN PedidoRepository
// import com.example.lenaycarbon.data.repository.PedidoRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date

data class SeguimientoUiState(
    val isLoading: Boolean = true,
    val pedido: Pedido? = null
)

// COLOCO AndroidViewModel PARA ACCEDER AL CONTEXTO DE LA BD IGUAL AL HomeViewModel
class SeguimientoViewModel(application: Application) : AndroidViewModel(application) {

    // LO QUE FALTA QUE AVANCEN ):
    // Falta que agreguen @Entity a la clase Pedido, creen 'PedidoDao' en la carpeta dao,
    // registren el DAO en 'AppDatabase' y creen el 'PedidoRepository'

    // CUANDO HAGAN ESO, PODRE IMPLEMENTAR ESTO:
    // private val pedidoRepository = PedidoRepository(application)

    private val _uiState = MutableStateFlow(SeguimientoUiState())
    val uiState: StateFlow<SeguimientoUiState> = _uiState.asStateFlow()

    fun cargarPedido(pedidoId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            /*
            pedidoRepository.obtenerPedidoPorId(pedidoId).collect { pedidoReal ->
                _uiState.value = SeguimientoUiState(
                    isLoading = false,
                    pedido = pedidoReal
                )
            }
            */

            // SIMULACIoN TEMPORAL
            // CUANDO ESTE INCORPORADO SE BORRA
            delay(1200) // SIMULA TIEMPO DE CARGA DEL ROM

            val pedidoSimulado = Pedido(
                id = pedidoId,
                codigo = 10025,
                idCliente = 1,
                estado = EstadoPedido.EN_PREPARACION,
                subTotal = 60.00,
                idTipoEntrega = 1,
                total = 72.90,
                observacion = "Llevar cremas",
                fechaPedido = Date(),
                tipoPago = TipoPago(1, "Yape")
            )

            _uiState.value = SeguimientoUiState(
                isLoading = false,
                pedido = pedidoSimulado
            )
        }
    }
}