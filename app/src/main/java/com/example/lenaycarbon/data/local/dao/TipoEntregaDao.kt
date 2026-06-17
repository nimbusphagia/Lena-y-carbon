package com.example.lenaycarbon.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.lenaycarbon.data.local.entity.TipoEntrega
import kotlinx.coroutines.flow.Flow

@Dao
interface TipoEntregaDao {
    @Insert
    fun insertTipoEntrega(
        tipoEntrega: TipoEntrega
    )

    @Update
    fun updateTipoEntrega(
        tipoEntrega: TipoEntrega
    )

    @Delete
    fun deleteTipoEntrega(
        tipoEntrega: TipoEntrega
    )

    @Query("SELECT * FROM tipoEntrega ORDER BY id DESC")
    fun listarTipoEntrega(): Flow<List<TipoEntrega>>
}