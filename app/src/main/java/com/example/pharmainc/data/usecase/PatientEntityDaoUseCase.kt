package com.example.pharmainc.data.usecase

import com.example.pharmainc.domain.error.type.ResultType
import com.example.pharmainc.presentation.model.Patient

interface PatientEntityDaoUseCase {

    suspend fun addPatient(patient: List<Patient>): ResultType<Long>

    suspend fun getAllPatient(): ResultType<List<Patient>>
}