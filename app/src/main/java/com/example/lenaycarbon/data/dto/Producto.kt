package com.example.lenaycarbon.data.dto

data class Producto(
    val id: Int,
    val nombre:String,
    val precio:Double,
    val descripcion:String,
    val imagen:Int,
    val disponible:Boolean, //estado
    val stock:Int,
    val categoria: CategoriaProducto, //idCategoria
)
