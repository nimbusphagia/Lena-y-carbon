package com.example.lenaycarbon.data.dto

import java.util.Date

data class Pedido(
    val id: Int,
    val codigo:Int,
    val idCliente: Int,
    val estado: EstadoPedido,
    val subTotal: Double,
    val idDelivery: Int,
    val total:Double,
    val observacion: String,
    val fechaPedido: Date,
    val tipoPago:TipoPago
)
