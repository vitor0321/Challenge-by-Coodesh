package com.example.pharmainc.domain.usecase.dao

import androidx.lifecycle.LiveData
import com.example.pharmainc.data.db.dao.PatientDao
import com.example.pharmainc.data.db.entity.PatientEntity

class PatientDAOUseCaseImpl(
    private val patientDao: PatientDao
) : PatientDAOUseCase {

    override suspend fun insertPatient(patient: PatientEntity): Long {
        return patientDao.save(patient)
    }

    override suspend fun getAllSubscriber(): LiveData<List<PatientEntity>> {
        return patientDao.getAll()
    }
}