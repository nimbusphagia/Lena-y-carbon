package com.example.lenaycarbon.data.dto

data class Producto(
    val nombre:String,
    val precio:Double,
    val descripcion:String,
    val imagen:Int,
    val disponibilidad:Boolean,
    val categoria: CategoriaProducto,
)
