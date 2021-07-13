package com.example.pharmainc.dataApi.model


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
    @SerializedName("login")
    val login: Login,
    @Url
    @SerializedName("dob")
    val dob: Dob,
    @Url
    @SerializedName("registered")
    val registered: Registered,
    @Url
    @SerializedName("phone")
    val phone: String,
    @Url
    @SerializedName("cell")
    val cell: String,
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