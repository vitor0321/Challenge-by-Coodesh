package com.example.pharmainc.data

import com.example.pharmainc.data.responseClient.CurrentPatientResponseApi
import retrofit2.Call
import retrofit2.http.GET

interface RandomUserService {
    @GET("api/")
    fun list(): Call<CurrentPatientResponseApi>
}