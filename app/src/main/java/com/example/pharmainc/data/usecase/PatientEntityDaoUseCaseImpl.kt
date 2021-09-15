package com.example.pharmainc.data.usecase

import com.example.pharmainc.data.db.dao.PatientDao
import com.example.pharmainc.data.network.webservice.PatientRepository
import com.example.pharmainc.domain.error.ErrorHandler
import com.example.pharmainc.domain.error.type.ResultType
import com.example.pharmainc.domain.mapper.dao.PatientMapperUseCase
import com.example.pharmainc.domain.mapper.dao.PatientEntityMapperUseCase
import com.example.pharmainc.presentation.model.Patient

class PatientEntityDaoUseCaseImpl(
    private val patientDAO: PatientDao,
    private val errorHandler: ErrorHandler,
    private val patientEntityMapperUseCase: PatientEntityMapperUseCase,
    private val entityPatientMapperUseCase: PatientMapperUseCase,
) : PatientEntityDaoUseCase {

    override suspend fun addPatient(patient: List<Patient>): ResultType<Long> {
        try {
            patientEntityMapperUseCase.fromEntityDaoList(patient).apply {
                var id: Long = 0
                this.map {
                    id = patientDAO.insert(it)
                }
                return ResultType.Success(id)
            }
        } catch (throwable: Throwable) {
            val error = errorHandler.getError(throwable)
            return ResultType.Error(error)
        }
    }

    override suspend fun getAllPatient(): ResultType<List<Patient>> {
        try {
            patientDAO.getAll().run {
                val listPatient: MutableList<Patient> = mutableListOf()
                if (this.value != null){
                    entityPatientMapperUseCase.fromViewList(this.value!!).apply {
                        this.map { patient ->
                            listPatient.add(patient)
                        }
                    }
                }
                return ResultType.Success(listPatient)
            }
        } catch (throwable: Throwable) {
            val error = errorHandler.getError(throwable)
            return ResultType.Error(error)
        }
    }
}