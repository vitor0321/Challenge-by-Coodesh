package com.example.pharmainc.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PatientResultsResponse(
    @Json(name = "patient_details")
    val patientDetailResponses: List<PatientDetailsResponse>
)