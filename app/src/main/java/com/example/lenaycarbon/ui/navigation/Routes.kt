package com.example.lenaycarbon.ui.navigation

object Routes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
    const val DETAIL = "detail/{productoId}"
    const val CARRITO = "carrito"

    const val SEGUIMIENTO = "seguimiento/{pedidoId}/{total}"
    const val DELIVERYTIPE = "deliveryType/{totalCarrito}"
    const val CONFIRMACION = "confirmacion"


    fun seguimientoRoute(pedidoId: Int, total: Double = 0.0): String {
        return "seguimiento/$pedidoId/$total"
    }

    fun confirmacionRoute(pedidoId: Int): String {
        return "confirmacion/$pedidoId"
    }

    fun detailRoute(productoId: Int): String {
        return "detail/$productoId"
    }
}