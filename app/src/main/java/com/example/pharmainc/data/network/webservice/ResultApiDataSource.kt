package com.example.pharmainc.data.network.webservice

import com.example.pharmainc.data.network.ApiService
import com.example.pharmainc.data.network.response.PatientBodyResponse
import com.example.pharmainc.domain.model.modelnetwork.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.suspendCoroutine

class ResultApiDataSource : ResultDataSource {

    override suspend fun getResult(): List<Result> {
        return suspendCoroutine { continuation ->
            ApiService.service.getResult().enqueue(object : Callback<PatientBodyResponse> {

                override fun onResponse(
                    call: Call<PatientBodyResponse>,
                    response: Response<PatientBodyResponse>
                ) {
                    response.isSuccessful.apply {
                        val patientList: MutableList<Result> = mutableListOf()
                        var count = 0
                        response.body()?.let { patientBodyResponse ->
                            patientBodyResponse.patientResults.map {
                                val patient =
                                    patientBodyResponse.patientResults[count].getPatientModel()
                                count++
                                patientList.add(patient)
                            }
                            continuation.resumeWith(kotlin.Result.success(patientList))
                        }
                    }
                }

                override fun onFailure(call: Call<PatientBodyResponse>, exception: Throwable) {
                    continuation.resumeWith(kotlin.Result.failure(exception))
                }
            })
        }
    }
}
