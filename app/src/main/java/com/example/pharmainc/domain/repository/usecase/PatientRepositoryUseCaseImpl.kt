package com.example.pharmainc.domain.repository.usecase

import androidx.lifecycle.LiveData
import com.example.pharmainc.data.db.entity.PatientEntity
import com.example.pharmainc.data.usecase.PatientEntityDaoUseCase
import com.example.pharmainc.data.usecase.ResultNetworkUseCase
import com.example.pharmainc.domain.error.type.ResultType
import com.example.pharmainc.domain.model.modelnetwork.Result

class PatientRepositoryUseCaseImpl(
    private val getResultUseCase: ResultNetworkUseCase,
    private val patientDaoUseCase: PatientEntityDaoUseCase
) : PatientRepositoryUseCase {

    override suspend fun getResultApi(): ResultType<List<Result>> {
        return getResultUseCase.invoke()
    }

    override suspend fun addPatientDao(patientEntity: PatientEntity): ResultType<Long> {
        return patientDaoUseCase.addPatient(patientEntity)
    }

    override suspend fun getAllPatientDao(): ResultType<LiveData<List<PatientEntity>>> {
        return patientDaoUseCase.getAllPatient()
    }
}