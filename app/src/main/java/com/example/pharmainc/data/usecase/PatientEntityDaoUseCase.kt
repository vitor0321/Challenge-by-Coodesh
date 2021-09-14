package com.example.pharmainc.data.usecase

import androidx.lifecycle.LiveData
import com.example.pharmainc.data.db.entity.PatientEntity
import com.example.pharmainc.domain.error.type.ResultType

interface PatientEntityDaoUseCase {

    suspend fun addPatient(patientEntity: PatientEntity): ResultType<Long>

    suspend fun getAllPatient(): ResultType<LiveData<List<PatientEntity>>>
}