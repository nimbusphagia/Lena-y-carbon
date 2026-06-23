package com.example.lenaycarbon.data.repository

import com.example.lenaycarbon.data.remote.RetrofitClient
import com.example.lenaycarbon.data.remote.dto.ReniecResponse

class DniRepository {
    private val token = "sk_16677.MAwOuxAlGb5m0foaufDYOrvaKLhpHHYj"

    suspend fun validarDni(dni: String): Result<ReniecResponse> {
        return try {
            val response = RetrofitClient.reniecService.consultarDni(dni, token)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}