package com.example.pharmainc.dataApi

import com.example.pharmainc.dataApi.response.PatientBodyResponse
import retrofit2.Call
import retrofit2.http.GET

interface RandomPatientService {
    @GET("api/")
    fun getPatient(): Call<PatientBodyResponse>
}