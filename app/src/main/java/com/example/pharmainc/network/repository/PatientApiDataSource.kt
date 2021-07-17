package com.example.pharmainc.network.repository

import com.example.pharmainc.network.ApiService
import com.example.pharmainc.network.PatientResult
import com.example.pharmainc.network.model.Result
import com.example.pharmainc.network.response.PatientBodyResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PatientApiDataSource : PatientRepository {
    override fun getPatient(patientResultCallback: (result: PatientResult) -> Unit) {

        ApiService.service.getPatient().enqueue(object : Callback<PatientBodyResponse> {
            override fun onResponse(
                call: Call<PatientBodyResponse>,
                response: Response<PatientBodyResponse>
            ) {
                when {
                    response.isSuccessful -> {
                        val patientList: MutableList<Result> = mutableListOf()
                        var count = 0
                        response.body()?.let { patientBodyResponse ->
                            for (result in patientBodyResponse.patientResults) {
                                val patient =
                                    patientBodyResponse.patientResults[count].getPatientModel()
                                count++
                                patientList.add(patient)
                            }
                            patientResultCallback(PatientResult.Success(patientList))
                        }
                    }
                    else -> patientResultCallback(PatientResult.ApiError(response.code()))
                }
            }

            override fun onFailure(call: Call<PatientBodyResponse>, t: Throwable) {
                patientResultCallback(PatientResult.ServerError)
            }
        })
    }
}