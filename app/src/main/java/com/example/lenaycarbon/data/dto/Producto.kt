package com.example.lenaycarbon.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class Producto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val precio: Double,
    val descripcion: String,
    val imagen: Int,
    val disponible: Boolean = true,
    val stock: Int = 0,
    @ColumnInfo(name = "idCategoria") val idCategoria: Int
)