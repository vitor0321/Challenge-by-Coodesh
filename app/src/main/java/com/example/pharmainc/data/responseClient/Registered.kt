package com.example.pharmainc.data.responseClient


import com.google.gson.annotations.SerializedName

data class Registered(
    @SerializedName("date")
    val date: String,
    @SerializedName("age")
    val age: Int
)