package com.example.lenaycarbon.data.dto

import com.example.lenaycarbon.data.local.entity.TipoPago
import java.util.Date

data class Pedido(
    val id: Int,
    val codigo:Int,
    val idCliente: Int,
    val estado: EstadoPedido,
    val subTotal: Double,
    val idTipoEntrega: Int,
    val total:Double,
    val observacion: String,
    val fechaPedido: Date,
    val tipoPago: TipoPago
)
