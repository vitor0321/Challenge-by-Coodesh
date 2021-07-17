package com.example.pharmainc.network.response

import com.example.pharmainc.network.model.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class  PatientDetailsResponse(
    @Json(name = "gender")
    val gender: String,
    @Json(name = "name")
    val name: Name,
    @Json(name = "location")
    val location: Location,
    @Json(name = "email")
    val email: String,
    @Json(name = "dob")
    val dob: Dob,
    @Json(name = "phone")
    val phone: String,
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
        dob = this.dob,
        phone = this.phone,
        id = this.id,
        picture = this.picture,
        nat = this.nat
    )
}