package com.example.pharmainc.framework.remote

import com.example.core.data.repository.PatientRemoteDataSource
import com.example.pharmainc.framework.network.response.PatientBodyResponse
import com.example.pharmainc.domain.model.modelnetwork.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.suspendCoroutine

class ResultApiDataSource : PatientRemoteDataSource<Result> {

    override suspend fun fetchCharacter(queries: Map<String, String>): Result {
        return suspendCoroutine { continuation ->
            RetrofitApiService.service.getResult().enqueue(object : Callback<PatientBodyResponse> {

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
