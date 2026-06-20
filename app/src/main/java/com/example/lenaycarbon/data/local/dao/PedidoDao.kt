package com.example.lenaycarbon.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.lenaycarbon.data.local.entity.PedidoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PedidoDao {

    // Esto lo usará Brando cuando el cliente confirme el carrito
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarPedido(pedido: PedidoEntity): Long

    // Para la pantalla de SEGUIMIENTO
    @Query("SELECT * FROM pedidos WHERE id = :pedidoId")
    fun obtenerPedidoPorId(pedidoId: Int): Flow<PedidoEntity>

    @Query("UPDATE pedidos SET estado = :nuevoEstado WHERE id = :pedidoId")
    suspend fun actualizarEstadoPedido(pedidoId: Int, nuevoEstado: String)

    //ESTE ES PARA QUE LA BD DEVUELVA 1 PEDIDO QUE NO ESTE ENTREGADO PARA CREAR UN BOTON
    // QUE APAREZCA SOLO CUANDO ESTE EN SEGUIMIENTO EL PEDIDO
    @Query("SELECT * FROM pedidos WHERE estado NOT IN ('ENTREGADO','CANCELADO') ORDER BY fechaPedido DESC LIMIT 1")
    fun obtenerPedidoActivo(): Flow<PedidoEntity>
}