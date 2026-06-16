package com.example.lenaycarbon.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "productos",
    foreignKeys = [
        ForeignKey(
            entity = CategoriaProducto::class,
            parentColumns = ["id"],
            childColumns = ["idCategoria"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["idCategoria"])]
)
data class Producto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val precio: Double,
    val descripcion: String,
    val imagen: String, // Cambie imgen Int -> String para almacenar los nombres en la bd
    val disponible: Boolean = true,
    val stock: Int = 0,
    @ColumnInfo(name = "idCategoria") val idCategoria: Int
)