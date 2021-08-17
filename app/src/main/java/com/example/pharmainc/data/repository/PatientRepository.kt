package com.example.pharmainc.data.repository

import com.example.pharmainc.domain.model.modelnetworl.Result

class PatientRepository(private val patientDataSource: PatientDataSource) {
    suspend fun getPatient(): List<Result> =
        patientDataSource.getPatient()
}