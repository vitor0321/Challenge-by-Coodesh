package com.example.pharmainc.data.repository

import com.example.pharmainc.domain.model.modelnetworl.Result

interface PatientDataSource {
    suspend fun getPatient(): List<Result>
}