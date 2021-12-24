package com.example.pharmainc.domain.repository.usecase

import com.example.pharmainc.domain.error.type.ResultType
import com.example.pharmainc.presentation.model.Patient

interface PatientRepositoryUseCase {

    suspend fun getResultApi(): ResultType<List<Patient>>
}