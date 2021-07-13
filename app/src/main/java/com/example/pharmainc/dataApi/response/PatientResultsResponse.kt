package com.example.pharmainc.dataApi.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PatientResultsResponse(
    @Json(name = "patientDetailResponses")
    val patientDetailResponses: List<PatientDetailsResponse>
)