package com.example.lenaycarbon.data.local.entity

import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tipoPago")
data class TipoPago(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,

    val imagen: Int = 0,
)