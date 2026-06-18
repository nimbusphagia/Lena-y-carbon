package com.example.lenaycarbon.data.local.dto

import com.example.lenaycarbon.data.local.entity.Producto

data class ItemCarrito(
    val producto: Producto, val cantidad: Int
) {
    val subtotal: Double get() = producto.precio * cantidad
}
