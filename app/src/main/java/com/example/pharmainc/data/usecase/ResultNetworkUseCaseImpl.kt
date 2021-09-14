package com.example.pharmainc.data.usecase

import com.example.pharmainc.data.network.webservice.PatientRepository
import com.example.pharmainc.domain.error.ErrorHandler
import com.example.pharmainc.domain.error.type.ResultType
import com.example.pharmainc.domain.model.modelnetwork.Result

class ResultNetworkUseCaseImpl(
    private val patientRepository: PatientRepository,
    private val errorHandler: ErrorHandler
) : ResultNetworkUseCase {
    override suspend fun invoke(): ResultType<List<Result>> {
        return try {
            patientRepository.getResult().run {
                ResultType.Success(this)
            }
        } catch (throwable: Throwable) {
            val error = errorHandler.getError(throwable)
            ResultType.Error(error)
        }
    }
}