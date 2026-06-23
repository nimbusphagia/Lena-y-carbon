package com.example.lenaycarbon.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lenaycarbon.data.local.dto.ItemCarrito
import com.example.lenaycarbon.data.local.entity.Producto
import com.example.lenaycarbon.data.repository.PedidoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.collections.toMutableList

class CarritoViewModel(application: Application) : AndroidViewModel(application) {
    private val pedidoRepository = PedidoRepository(application)

    private val _items = MutableStateFlow<List<ItemCarrito>>(emptyList())
    val items: StateFlow<List<ItemCarrito>> = _items.asStateFlow()

    private val _pedidoEnCurso = MutableStateFlow(false)
    val pedidoEnCurso: StateFlow<Boolean> = _pedidoEnCurso.asStateFlow()

    init {
        viewModelScope.launch {
            pedidoRepository.obtenerPedidoActivo().collect { pedidoActivo ->
                _pedidoEnCurso.value = pedidoActivo != null
            }
        }
    }

    val total: StateFlow<Double>
        get() = MutableStateFlow(
            _items.value.sumOf { it.subtotal }).asStateFlow()

    fun agregarProducto(producto: Producto, cantidad: Int) {
        val listaActual = _items.value.toMutableList()
        val index = listaActual.indexOfFirst { it.producto.id == producto.id }

        if (index >= 0) {
            val itemExistente = listaActual[index]
            listaActual[index] = itemExistente.copy(cantidad = itemExistente.cantidad + cantidad)
        } else {
            listaActual.add(ItemCarrito(producto, cantidad))
        }
        _items.value = listaActual
    }

    fun eliminarProducto(productoId: Int) {
        _items.value = _items.value.filter { it.producto.id != productoId }
    }

    fun limpiarCarrito() {
        _items.value = emptyList()
    }

    fun calcularTotal(): Double = _items.value.sumOf { it.subtotal }

    fun actualizarCantidad(productoId: Int, nuevaCantidad: Int) {
        val listaActual = _items.value.toMutableList()
        val index = listaActual.indexOfFirst { it.producto.id == productoId }

        if (index >= 0) {
            if (nuevaCantidad <= 0) {
                // Si baja a 0 o menos, eliminamos el producto
                listaActual.removeAt(index)
            } else {
                // Actualizamos la cantidad
                val itemExistente = listaActual[index]
                listaActual[index] = itemExistente.copy(cantidad = nuevaCantidad)
            }
            _items.value = listaActual
        }
    }
}