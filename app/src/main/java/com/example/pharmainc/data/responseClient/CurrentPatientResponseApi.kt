package com.example.pharmainc.data.responseClient


import com.google.gson.annotations.SerializedName
import retrofit2.http.Url

data class CurrentPatientResponseApi(
    @Url
    @SerializedName("results")
    val results: List<Result>,
)