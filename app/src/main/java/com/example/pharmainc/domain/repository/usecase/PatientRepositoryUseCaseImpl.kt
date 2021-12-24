package com.example.pharmainc.domain.repository.usecase

import com.example.pharmainc.data.usecase.ResultNetworkUseCase
import com.example.pharmainc.domain.error.type.ResultType
import com.example.pharmainc.presentation.model.Patient

class PatientRepositoryUseCaseImpl(
    private val getResultUseCase: ResultNetworkUseCase,
) : PatientRepositoryUseCase {

    override suspend fun getResultApi(): ResultType<List<Patient>> {
        return getResultUseCase.invoke()
    }
}