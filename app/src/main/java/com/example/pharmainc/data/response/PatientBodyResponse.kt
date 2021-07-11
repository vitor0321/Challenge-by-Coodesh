package com.example.pharmainc.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PatientBodyResponse(
    @Json(name = "patient_results")
    val patientResults: List<PatientResultsResponse>
)
