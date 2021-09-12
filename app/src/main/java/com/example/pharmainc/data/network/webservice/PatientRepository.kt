package com.example.pharmainc.data.network.webservice

import com.example.pharmainc.domain.model.modelnetwork.Result

class PatientRepository(private val patientDataSource: ResultDataSource) {
    suspend fun getResult(): List<Result> =
        patientDataSource.getResult()
}