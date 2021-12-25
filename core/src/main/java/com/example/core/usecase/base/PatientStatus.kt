package com.example.core.usecase.base

sealed class PatientStatus<out T> {

    object Loading : PatientStatus<Nothing>()
    data class Success<out T>(val data: T) : PatientStatus<T>()
    data class Error(val throwable: Throwable) : PatientStatus<Nothing>()


    override fun toString(): String {
        return when (this) {
            Loading -> "Loading"
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[throwable=$throwable]"
        }
    }
}