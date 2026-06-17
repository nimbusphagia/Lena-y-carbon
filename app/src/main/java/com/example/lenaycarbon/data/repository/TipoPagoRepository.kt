package com.example.lenaycarbon.data.repository

import android.content.Context
import com.example.lenaycarbon.data.local.AppDatabase
import com.example.lenaycarbon.data.local.entity.TipoPago
import com.example.lenaycarbon.data.local.dao.TipoPagoDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Contextual

class TipoPagoRepository (
   context: Context
) {
    private val db = AppDatabase.getDatabase(context)
    private val tipoPagodao = db.tipoPagoDao()
    val tiposPago = tipoPagodao.listarTipoPago()

    suspend fun insertarTipoPago(tipoPago: TipoPago){
        withContext(Dispatchers.IO) {
            tipoPagodao.insertTipoPago(tipoPago)
        }
    }

    suspend fun eliminarTipoPago(tipoPago: TipoPago){
        withContext(Dispatchers.IO) {
            tipoPagodao.deleteTipoPago(tipoPago)
        }
    }

    suspend fun actualizarTipoPago(tipoPago: TipoPago){
        withContext(Dispatchers.IO) {
            tipoPagodao.updateTipoPago(tipoPago)
        }
    }
}