package com.example.lenaycarbon.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val reniecService: ReniecService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.decolecta.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ReniecService::class.java)
    }
}