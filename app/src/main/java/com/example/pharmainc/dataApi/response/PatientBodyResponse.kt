package com.example.pharmainc.dataApi.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PatientBodyResponse(
    @Json(name = "results")
    val patientResults: List<PatientResultsResponse>
)
