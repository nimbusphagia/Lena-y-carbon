package com.example.lenaycarbon.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ReniecResponse(
    @SerializedName("first_name") val nombres: String,
    @SerializedName("first_last_name") val apellidoPaterno: String,
    @SerializedName("second_last_name") val apellidoMaterno: String,
    @SerializedName("document_number") val numeroDocumento: String
)

