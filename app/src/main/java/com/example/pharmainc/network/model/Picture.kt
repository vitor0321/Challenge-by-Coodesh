package com.example.pharmainc.network.model


import com.google.gson.annotations.SerializedName

data class Picture(
    @SerializedName("large")
    val large: String,
)