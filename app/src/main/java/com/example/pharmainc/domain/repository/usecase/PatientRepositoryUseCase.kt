package com.example.pharmainc.domain.repository.usecase

import com.example.core.domain.error.type.ResultType
import com.example.core.domain.model.Patient

interface PatientRepositoryUseCase {

    suspend fun getResultApi(): ResultType<List<Patient>>
}