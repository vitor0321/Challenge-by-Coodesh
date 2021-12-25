package com.example.pharmainc.framework.network

import com.example.pharmainc.framework.network.response.PatientBodyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomApi {
    @GET("api/")
    fun getResult(
        @Query("results")
        queries: Map<String, String>
    ): Call<PatientBodyResponse>
}