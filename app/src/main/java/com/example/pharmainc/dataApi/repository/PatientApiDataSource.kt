package com.example.pharmainc.dataApi.repository

import com.example.pharmainc.dataApi.ApiService
import com.example.pharmainc.dataApi.PatientResult
import com.example.pharmainc.dataApi.model.CurrentPatientResponseApi
import com.example.pharmainc.dataApi.model.Result
import com.example.pharmainc.dataApi.response.PatientBodyResponse
import com.example.pharmainc.ui.model.ItemPatient
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
