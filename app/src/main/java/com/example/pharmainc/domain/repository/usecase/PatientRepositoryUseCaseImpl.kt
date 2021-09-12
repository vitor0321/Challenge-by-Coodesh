package com.example.pharmainc.domain.repository.usecase

import com.example.pharmainc.data.network.usecase.GetResultUseCase
import com.example.pharmainc.domain.error.type.ResultType
import com.example.pharmainc.domain.model.modelnetwork.Result

class PatientRepositoryUseCaseImpl(
    private val getResultUseCase: GetResultUseCase,
) : PatientRepositoryUseCase {
    override suspend fun getResultApi(): ResultType<List<Result>> {
        return getResultUseCase.invoke()
    }
}