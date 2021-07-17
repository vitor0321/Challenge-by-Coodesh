package com.example.pharmainc.dataApi.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.http.Query

@JsonClass(generateAdapter = true)
data class PatientBodyResponse(
    @Json(name = "results")
    val patientResults: List<PatientDetailsResponse>
)
