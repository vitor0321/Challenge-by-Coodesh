package com.example.pharmainc.dataApi.model


import com.google.gson.annotations.SerializedName

data class Picture(
    @SerializedName("medium")
    val medium: String,
)