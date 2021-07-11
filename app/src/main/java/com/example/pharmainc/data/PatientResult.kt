package com.example.pharmainc.data

import com.example.pharmainc.data.model.Result

sealed class PatientResult {
    class Success(val patient: List<Result>) : PatientResult()
    class ApiError(val statusCode: Int) : PatientResult()
    object ServerError : PatientResult()
}
