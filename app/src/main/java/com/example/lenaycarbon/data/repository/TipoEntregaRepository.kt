package com.example.lenaycarbon.data.repository

import android.content.Context
import com.example.lenaycarbon.data.local.AppDatabase
import com.example.lenaycarbon.data.local.entity.TipoEntrega
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class TipoEntregaRepository(
    context: Context
) {
    private val db = AppDatabase.getDatabase(context)
    private val tipoEntregaDao = db.tipoEntregaDao()

    val tiposEntrega: Flow<List<TipoEntrega>> = tipoEntregaDao.listarTipoEntrega()

    suspend fun insertarTipoEntrega(tipoEntrega: TipoEntrega) {
        withContext(Dispatchers.IO) {
            tipoEntregaDao.insertTipoEntrega(tipoEntrega)
        }
    }

    suspend fun eliminarTipoEntrega(tipoEntrega: TipoEntrega) {
        withContext(Dispatchers.IO) {
            tipoEntregaDao.deleteTipoEntrega(tipoEntrega)
        }
    }

    suspend fun actualizarTipoEntrega(tipoEntrega: TipoEntrega) {
        withContext(Dispatchers.IO) {
            tipoEntregaDao.updateTipoEntrega(tipoEntrega)
        }
    }
}