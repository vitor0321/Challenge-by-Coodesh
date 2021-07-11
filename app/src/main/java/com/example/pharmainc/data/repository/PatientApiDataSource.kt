package com.example.pharmainc.data.repository

import com.example.pharmainc.data.ApiService
import com.example.pharmainc.data.PatientResult
import com.example.pharmainc.data.model.Result
import com.example.pharmainc.data.response.PatientBodyResponse
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
                        response.body()?.let { patientBodyResponse ->
                            for (result in patientBodyResponse.patientResults) {
                                val patient = result.patientDetailResponses[0].getPatientModel()
                                patientList.add(patient)
                            }
                        }
                        patientResultCallback(PatientResult.Success(patientList))
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
