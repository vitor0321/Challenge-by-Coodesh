package com.example.pharmainc.domain.usecase.network

import com.example.pharmainc.data.network.webservice.PatientRepository
import com.example.pharmainc.domain.model.modelnetworl.Result
import com.example.pharmainc.domain.usecase.network.GetResultUseCase

class GetResultUseCaseImpl(
    private val patientRepository: PatientRepository
) : GetResultUseCase {
    override suspend fun invoke(): List<Result> {
        return patientRepository.getPatient()
    }
}