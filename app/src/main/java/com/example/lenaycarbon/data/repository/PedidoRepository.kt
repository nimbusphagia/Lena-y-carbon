package com.example.lenaycarbon.data.repository

import android.content.Context
import com.example.lenaycarbon.data.local.AppDatabase
import com.example.lenaycarbon.data.local.entity.PedidoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class PedidoRepository(context: Context) {

    private val db = AppDatabase.getDatabase(context)
    private val pedidoDao = db.pedidoDao()

    // para la pantalla de Seguimiento
    fun obtenerPedidoPorId(id: Int): Flow<PedidoEntity> = pedidoDao.obtenerPedidoPorId(id)

    // para la pantalla de Confirmación (Brando)
    suspend fun insertarPedido(pedido: PedidoEntity): Long {
        return withContext(Dispatchers.IO) {
            pedidoDao.insertarPedido(pedido)
        }
    }

    //para darle un tiempo de carga a cada estado de seguimiento del pedido
    suspend fun actualizarEstadoPedido(pedidoId: Int, nuevoEstado: String) {
        withContext(Dispatchers.IO) {
            pedidoDao.actualizarEstadoPedido(pedidoId, nuevoEstado)
        }
    }

    fun obtenerPedidoActivo(): Flow<PedidoEntity?> = pedidoDao.obtenerPedidoActivo()
}