package com.example.pharmainc.domain.model.modelnetwork


import com.google.gson.annotations.SerializedName
import retrofit2.http.Url

data class Result(
    @Url
    @SerializedName("gender")
    val gender: String,
    @Url
    @SerializedName("name")
    val name: Name,
    @Url
    @SerializedName("location")
    val location: Location,
    @Url
    @SerializedName("email")
    val email: String,
    @Url
    @SerializedName("dob")
    val dob: Dob,
    @Url
    @SerializedName("phone")
    val phone: String,
    @Url
    @SerializedName("id")
    val id: Id,
    @Url
    @SerializedName("picture")
    val picture: Picture,
    @Url
    @SerializedName("nat")
    val nat: String
)