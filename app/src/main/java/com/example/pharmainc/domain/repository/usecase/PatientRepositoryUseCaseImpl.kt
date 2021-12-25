package com.example.pharmainc.domain.repository.usecase

import com.example.core.data.repository.ResultNetworkUseCase
import com.example.core.domain.error.type.ResultType
import com.example.core.domain.model.Patient

class PatientRepositoryUseCaseImpl(
    private val getResultUseCase: ResultNetworkUseCase,
) : PatientRepositoryUseCase {

    override suspend fun getResultApi(): ResultType<List<Patient>> {
        return getResultUseCase.invoke()
    }
}