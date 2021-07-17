package com.example.pharmainc.network.repository

import com.example.pharmainc.network.PatientResult

interface PatientRepository {
    fun getPatient(patientResultCallback: (result: PatientResult) -> Unit)
}