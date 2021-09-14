package com.example.pharmainc.data.usecase

import androidx.lifecycle.LiveData
import com.example.pharmainc.data.db.dao.PatientDao
import com.example.pharmainc.data.db.entity.PatientEntity
import com.example.pharmainc.domain.error.ErrorHandler
import com.example.pharmainc.domain.error.type.ResultType

class PatientEntityDaoUseCaseImpl(
    private val patientDAO: PatientDao,
    private val errorHandler: ErrorHandler
) : PatientEntityDaoUseCase {

    override suspend fun addPatient(patientEntity: PatientEntity): ResultType<Long> {
        return try {
            patientDAO.insert(patientEntity).run {
                ResultType.Success(this)
            }
        } catch (throwable: Throwable) {
            val error = errorHandler.getError(throwable)
            ResultType.Error(error)
        }

    }

    override suspend fun getAllPatient(): ResultType<LiveData<List<PatientEntity>>> {
        return try {
            patientDAO.getAll().run {
                ResultType.Success(this)
            }
        } catch (throwable: Throwable) {
            val error = errorHandler.getError(throwable)
            ResultType.Error(error)
        }
    }
}