package com.example.pharmainc.data.repository

import com.example.pharmainc.data.PatientResult

interface PatientRepository {
    fun getPatient(patientResultCallback: (result: PatientResult) -> Unit)
}