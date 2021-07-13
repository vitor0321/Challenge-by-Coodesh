package com.example.pharmainc.dataApi

import com.example.pharmainc.dataApi.model.Result

sealed class PatientResult {
    class Success(val patient: List<Result>) : PatientResult()
    class ApiError(val statusCode: Int) : PatientResult()
    object ServerError : PatientResult()
}
