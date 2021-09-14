package com.example.pharmainc.data.network.response

import com.example.pharmainc.domain.model.modelnetwork.Result
import com.example.pharmainc.domain.model.modelnetwork.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class  PatientDetailsResponse(
    @Json(name = "gender")
    val gender: String,
    @Json(name = "name")
    val name: Name,
    @Json(name = "email")
    val email: String,
    @Json(name = "location")
    val location: Location,
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
        gender = gender,
        name = name,
        location = location,
        email = email,
        dob = dob,
        phone = phone,
        id = id,
        picture = picture,
        nat = nat
    )
}