package com.example.lenaycarbon.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categorias")
data class CategoriaProducto(
    @PrimaryKey val id: Int,
    val nombre: String,
    val estado: Boolean = true
)