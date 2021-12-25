package com.example.pharmainc.data.usecase

import com.example.core.data.repository.ResultNetworkUseCase
import com.example.pharmainc.data.network.webservice.PatientRepository
import com.example.core.domain.error.ErrorHandler
import com.example.core.domain.error.type.ResultType
import com.example.pharmainc.domain.mapper.network.ResultMapperUseCase
import com.example.core.domain.model.Patient
import java.io.IOException

class ResultNetworkUseCaseImpl(
    private val patientRepository: PatientRepository,
    private val errorHandler: ErrorHandler,
    private val resultMapperUseCase: ResultMapperUseCase,
) : ResultNetworkUseCase {

    override suspend fun invoke(): ResultType<List<Patient>> {
        try {
            patientRepository.getResult().run {
                resultMapperUseCase.fromEntityApiList(this).apply {
                    return ResultType.Success(this)
                }
            }
        } catch (throwable: IOException) {
            val error = errorHandler.getError(throwable)
            return ResultType.Error(error)
        }
    }
}