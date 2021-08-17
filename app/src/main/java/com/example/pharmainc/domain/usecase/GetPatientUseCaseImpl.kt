package com.example.pharmainc.domain.usecase

import com.example.pharmainc.data.repository.PatientRepository
import com.example.pharmainc.domain.model.modelnetworl.Result

class GetPatientUseCaseImpl(
    private val patientRepository: PatientRepository
) : GetPatientUseCase {
    override suspend fun invoke(): List<Result> {
        return patientRepository.getPatient()
    }
}