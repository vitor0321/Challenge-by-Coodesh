package com.example.pharmainc.domain.repository.usecase

import com.example.pharmainc.data.usecase.PatientEntityDaoUseCase
import com.example.pharmainc.data.usecase.ResultNetworkUseCase
import com.example.pharmainc.domain.error.type.ResultType
import com.example.pharmainc.presentation.model.Patient

class PatientRepositoryUseCaseImpl(
    private val getResultUseCase: ResultNetworkUseCase,
    private val patientDaoUseCase: PatientEntityDaoUseCase
) : PatientRepositoryUseCase {

    override suspend fun getResultApi(): ResultType<List<Patient>> {
        return getResultUseCase.invoke()
    }

    override suspend fun addPatientDao(patient: List<Patient>):ResultType<Long> {
        return patientDaoUseCase.addPatient(patient)
    }

    override suspend fun getAllPatientDao(): ResultType<List<Patient>> {
        return patientDaoUseCase.getAllPatient()
    }
}