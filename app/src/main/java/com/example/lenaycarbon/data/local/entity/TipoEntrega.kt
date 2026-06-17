package com.example.lenaycarbon.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tipoEntrega" )
data class TipoEntrega(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val precio:Double,
    val nombre: String,
    val imagen: Int,
)