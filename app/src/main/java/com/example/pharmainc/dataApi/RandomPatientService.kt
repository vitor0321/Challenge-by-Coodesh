package com.example.pharmainc.dataApi

import com.example.pharmainc.dataApi.response.PatientBodyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomPatientService {
    @GET("api/")
    fun getPatient(
        @Query( "results") results: Int = 50,
        ): Call<PatientBodyResponse>
}