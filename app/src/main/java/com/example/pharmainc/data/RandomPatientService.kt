package com.example.pharmainc.data

import com.example.pharmainc.data.response.PatientBodyResponse
import retrofit2.Call
import retrofit2.http.GET

interface RandomPatientService {
    @GET("api/")
    fun getPatient(): Call<PatientBodyResponse>
}