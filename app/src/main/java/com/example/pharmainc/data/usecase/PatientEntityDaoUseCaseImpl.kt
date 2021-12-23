package com.example.pharmainc.data.usecase

import com.example.pharmainc.data.db.dao.PatientDao
import com.example.pharmainc.domain.error.ErrorHandler
import com.example.pharmainc.domain.error.type.ResultType
import com.example.pharmainc.domain.mapper.dao.PatientEntityMapperUseCase
import com.example.pharmainc.domain.mapper.dao.PatientMapperUseCase
import com.example.pharmainc.presentation.model.Patient
import kotlinx.coroutines.flow.first
import okio.IOException

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
        } catch (throwable: IOException) {
            val error = errorHandler.getError(throwable)
            return ResultType.Error(error)
        }
    }

    override suspend fun getAllPatient(): ResultType<MutableList<Patient>> {
        return try {
            val listPatient: MutableList<Patient> = mutableListOf()
            patientDAO.getAll().apply{
                entityPatientMapperUseCase.fromViewList(this.first()).apply {
                    this.map { patient ->
                        listPatient.add(patient)
                    }
                }
            }
            ResultType.Success(listPatient)
        } catch (throwable: IOException) {
            val error = errorHandler.getError(throwable)
            ResultType.Error(error)
        }
    }
}