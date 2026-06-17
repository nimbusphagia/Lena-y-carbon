package com.example.lenaycarbon.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.lenaycarbon.data.local.entity.TipoPago
import kotlinx.coroutines.flow.Flow

@Dao
interface TipoPagoDao {

    @Insert
    fun insertTipoPago(
        tipoPago: TipoPago
    )

    @Update
    fun updateTipoPago(
        tipoPago: TipoPago
    )

    @Delete
    fun deleteTipoPago(
        tipoPago: TipoPago
    )

    @Query("SELECT * FROM tipoPago ORDER BY id DESC")
    fun listarTipoPago(): Flow<List<TipoPago>>
}