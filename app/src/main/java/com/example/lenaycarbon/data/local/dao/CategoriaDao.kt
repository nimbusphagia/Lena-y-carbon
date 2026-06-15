package com.example.lenaycarbon.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lenaycarbon.data.dto.CategoriaProducto
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarCategorias(categorias: List<CategoriaProducto>)

    @Query("SELECT * FROM categorias WHERE estado = 1 ORDER BY nombre ASC")
    fun obtenerCategoriasActivas(): Flow<List<CategoriaProducto>>

    @Query("SELECT COUNT(*) FROM categorias")
    suspend fun contarCategorias(): Long
}