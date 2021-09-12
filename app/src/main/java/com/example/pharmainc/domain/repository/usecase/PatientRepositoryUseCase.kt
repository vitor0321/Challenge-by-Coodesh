package com.example.pharmainc.domain.repository.usecase

import com.example.pharmainc.domain.error.type.ResultType
import com.example.pharmainc.domain.model.modelnetwork.Result

interface PatientRepositoryUseCase {

    suspend fun getResultApi(): ResultType<List<Result>>

}