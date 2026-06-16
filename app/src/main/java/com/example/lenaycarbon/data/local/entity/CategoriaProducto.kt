package com.example.lenaycarbon.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categorias")
data class CategoriaProducto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    @ColumnInfo(defaultValue = "1") val estado: Boolean = true
)