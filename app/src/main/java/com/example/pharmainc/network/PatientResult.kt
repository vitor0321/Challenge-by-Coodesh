package com.example.pharmainc.network

import com.example.pharmainc.network.model.Result

sealed class PatientResult {
    class Success(val patient: List<Result>) : PatientResult()
    class ApiError(val statusCode: Int) : PatientResult()
    object ServerError : PatientResult()
}
