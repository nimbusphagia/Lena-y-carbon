package com.example.lenaycarbon.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lenaycarbon.data.dto.Producto
import com.example.lenaycarbon.data.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ProductoRepository(application)

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    private val _categoriaSeleccionada = MutableStateFlow<Int?>(null)
    val categoriaSeleccionada: StateFlow<Int?> = _categoriaSeleccionada.asStateFlow()

    private val _busqueda = MutableStateFlow("")
    val busqueda: StateFlow<String> = _busqueda.asStateFlow()

    init {
        viewModelScope.launch {
            repository.inicializarSiEstaVacia()
            repository.obtenerProductos().collect { _productos.value = it }
        }
    }

    fun seleccionarCategoria(id: Int?) {
        _categoriaSeleccionada.value = id
        viewModelScope.launch {
            val flow = if (id == null) repository.obtenerProductos() else repository.obtenerPorCategoria(id)
            flow.collect { _productos.value = it }
        }
    }

    fun buscarPorNombre(query: String) {
        _busqueda.value = query
        viewModelScope.launch {
            val flow = if (query.isBlank()) repository.obtenerProductos() else repository.buscarPorNombre(query)
            flow.collect { _productos.value = it }
        }
    }
}