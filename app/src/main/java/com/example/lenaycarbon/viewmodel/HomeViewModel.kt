package com.example.lenaycarbon.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lenaycarbon.data.local.AppDatabase
import com.example.lenaycarbon.data.local.entity.CategoriaProducto
import com.example.lenaycarbon.data.local.entity.PedidoEntity
import com.example.lenaycarbon.data.local.entity.Producto
import com.example.lenaycarbon.data.repository.PedidoRepository
import com.example.lenaycarbon.data.repository.ProductoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val pedidoRepository = PedidoRepository(application)
    private val _pedidoActivo = MutableStateFlow<PedidoEntity?>(null)
    val pedidoActivo: StateFlow<PedidoEntity?> = _pedidoActivo.asStateFlow()

    init {
        viewModelScope.launch {
            pedidoRepository.obtenerPedidoActivo().collect { pedido ->
                _pedidoActivo.value = pedido
            }
        }
    }

    private val repository = ProductoRepository(application)
    val categorias: StateFlow<List<CategoriaProducto>> = repository.obtenerCategorias()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    private val _categoriaSeleccionada = MutableStateFlow<Int?>(null)
    val categoriaSeleccionada: StateFlow<Int?> = _categoriaSeleccionada.asStateFlow()

    private val _busqueda = MutableStateFlow("")
    val busqueda: StateFlow<String> = _busqueda.asStateFlow()
    val productos: StateFlow<List<Producto>> = combine(
        _categoriaSeleccionada,
        _busqueda
    ) { categoria, busqueda ->
        Pair(categoria, busqueda)
    }.flatMapLatest { (categoria, busqueda) ->
        when {
            busqueda.isNotBlank() -> repository.buscarPorNombre(busqueda)
            categoria != null -> repository.obtenerPorCategoria(categoria)
            else -> repository.obtenerProductos()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun seleccionarCategoria(id: Int?) {
        _categoriaSeleccionada.value = id
        _busqueda.value = ""
    }

    fun buscarPorNombre(query: String) {
        _busqueda.value = query
    }
}