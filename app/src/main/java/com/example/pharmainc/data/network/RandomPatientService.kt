package com.example.pharmainc.data.network

import com.example.pharmainc.data.network.response.PatientBodyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomPatientService {
    @GET("api/")
    fun getPatient(@Query("results") results: Int = 50): Call<PatientBodyResponse>
}