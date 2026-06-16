package com.example.lenaycarbon.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lenaycarbon.data.local.entity.Producto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarProductos(productos: List<Producto>)

    @Query("SELECT * FROM productos WHERE disponible = 1 ORDER BY nombre ASC")
    fun obtenerProductosDisponibles(): Flow<List<Producto>>

    @Query("SELECT * FROM productos WHERE idCategoria = :idCategoria AND disponible = 1 ORDER BY nombre ASC")
    fun obtenerPorCategoria(idCategoria: Int): Flow<List<Producto>>

    @Query("SELECT * FROM productos WHERE nombre LIKE '%' || :query || '%' AND disponible = 1 ORDER BY nombre ASC")
    fun buscarPorNombre(query: String): Flow<List<Producto>>

    @Query("SELECT COUNT(*) FROM productos")
    suspend fun contarProductos(): Long
}