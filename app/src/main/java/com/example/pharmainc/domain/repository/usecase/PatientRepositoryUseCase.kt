package com.example.pharmainc.domain.repository.usecase

import androidx.lifecycle.LiveData
import com.example.pharmainc.data.db.entity.PatientEntity
import com.example.pharmainc.domain.error.type.ResultType
import com.example.pharmainc.presentation.model.Patient

interface PatientRepositoryUseCase {

    suspend fun getResultApi(): ResultType<List<Patient>>

    suspend fun addPatientDao(patient: List<Patient>):ResultType<Long>

    suspend fun getAllPatientDao(): ResultType<List<Patient>>
}