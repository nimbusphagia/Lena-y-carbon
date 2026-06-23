package com.example.lenaycarbon.data.remote

import com.example.lenaycarbon.data.remote.dto.ReniecResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ReniecService {
    @GET("v1/reniec/dni")
    suspend fun consultarDni(
        @Query("numero") dni: String,
        @Header("Authorization") token: String
    ): ReniecResponse
}