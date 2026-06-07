package com.example.lenaycarbon.data.dto

data class Usuario(
   val id: Int,
   val nombre: String,
   val apellidos: String,
   val telefono: String,
   val password: String,
   val email: String,
   val direccion: String,
   val estado: Boolean
)
