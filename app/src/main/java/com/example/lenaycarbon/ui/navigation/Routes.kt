package com.example.lenaycarbon.ui.navigation

object Routes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
    const val DETAIL = "detail/{productoId}"
    const val CARRITO = "carrito"

    //RUTA A ESTADO DE PEDIDO (FABRIZIO)
    const val SEGUIMIENTO = "seguimiento/{pedidoId}"

    //RUTA DE ACCESO A la confirmación de Pedido(brando)
    const val CONFIRMACION = "confirmacion"

    // FUNCION DE ESTADO DE PEDIDO (FABRIZIO)
    fun seguimientoRoute(pedidoId: Int):String{
        return "seguimiento/$pedidoId"
    }

    fun confirmacionRoute(pedidoId: Int):String{
        return "confirmacion/$pedidoId"
    }

    fun detailRoute(productoId: Int): String {
        return "detail/$productoId"
    }


}