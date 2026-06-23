package com.example.lenaycarbon.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pedidos")
data class PedidoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val codigo: Int,
    val dni: String,
    val estado: String,
    val subTotal: Double,
    val idTipoEntrega: Int,
    val direccion: String? = null,
    val total: Double,
    val observacion: String,
    val fechaPedido: Long,
    val idTipoPago: Int
)