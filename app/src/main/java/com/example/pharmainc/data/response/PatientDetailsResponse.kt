package com.example.pharmainc.data.response

import com.example.pharmainc.data.model.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PatientDetailsResponse(
    @Json(name = "gender")
    val gender: String,
    @Json(name = "name")
    val name: Name,
    @Json(name = "location")
    val location: Location,
    @Json(name = "email")
    val email: String,
    @Json(name = "login")
    val login: Login,
    @Json(name = "dob")
    val dob: Dob,
    @Json(name = "registered")
    val registered: Registered,
    @Json(name = "phone")
    val phone: String,
    @Json(name = "cell")
    val cell: String,
    @Json(name = "id")
    val id: Id,
    @Json(name = "picture")
    val picture: Picture,
    @Json(name = "nat")
    val nat: String
) {
    fun getPatientModel() = Result(
        gender = this.gender,
        name = this.name,
        location = this.location,
        email = this.email,
        login = this.login,
        dob = this.dob,
        registered = this.registered,
        phone = this.phone,
        cell = this.cell,
        id = this.id,
        picture = this.picture,
        nat = this.nat
    )
}