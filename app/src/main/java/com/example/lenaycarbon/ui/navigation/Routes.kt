package com.example.lenaycarbon.ui.navigation

object Routes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val HOME = "home"
    const val DETAIL = "detail/{productoId}"

    //RUTA A ESTADO DE PEDIDO (FABRIZIO)
    const val SEGUIMIENTO = "seguimiento/{pedidoId}"

    // FUNCION DE ESTADO DE PEDIDO (FABRIZIO)
    fun seguimientoRoute(pedidoId: Int):String{
        return "seguimiento/$pedidoId"
    }

    fun detailRoute(productoId: Int): String {
        return "detail/$productoId"
    }


}