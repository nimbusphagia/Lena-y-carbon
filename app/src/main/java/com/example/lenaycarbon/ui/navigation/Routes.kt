package com.example.lenaycarbon.ui.navigation

object Routes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val HOME = "home"
    const val DETAIL = "detail/{productoId}"

    const val DELIVERYTYPE = "deliveryType/{totalCarrito}"

    fun detailRoute(productoId: Int): String {
        return "detail/$productoId"
    }
}