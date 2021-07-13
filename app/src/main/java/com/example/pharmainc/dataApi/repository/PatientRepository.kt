package com.example.pharmainc.dataApi.repository

import com.example.pharmainc.dataApi.PatientResult

interface PatientRepository {
    fun getPatient(patientResultCallback: (result: PatientResult) -> Unit)
}