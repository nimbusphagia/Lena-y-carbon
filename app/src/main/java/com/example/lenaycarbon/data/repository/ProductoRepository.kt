package com.example.lenaycarbon.data.repository

import android.content.Context
import com.example.lenaycarbon.data.local.entity.CategoriaProducto
import com.example.lenaycarbon.data.local.entity.Producto
import com.example.lenaycarbon.data.local.AppDatabase
import com.example.lenaycarbon.data.mockup.listaCategorias
import com.example.lenaycarbon.data.mockup.listaProductos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ProductoRepository(context: Context) {
    private val db = AppDatabase.getDatabase(context)
    private val productoDao = db.productoDao()
    private val categoriaDao = db.categoriaDao()

    // Productos
    fun obtenerProductos(): Flow<List<Producto>> = productoDao.obtenerProductosDisponibles()
    fun obtenerPorCategoria(idCategoria: Int): Flow<List<Producto>> =
        productoDao.obtenerPorCategoria(idCategoria)

    fun buscarPorNombre(query: String): Flow<List<Producto>> = productoDao.buscarPorNombre(query)

    // Categorías
    fun obtenerCategorias(): Flow<List<CategoriaProducto>> = categoriaDao.obtenerCategoriasActivas()

    // Inicialización conjunta
    suspend fun inicializarSiEstaVacia() {
        withContext(Dispatchers.IO) {
            if (categoriaDao.contarCategorias() == 0L) {
                categoriaDao.insertarCategorias(listaCategorias)
            }
            if (productoDao.contarProductos() == 0L) {
                productoDao.insertarProductos(listaProductos)
            }
        }
    }
}