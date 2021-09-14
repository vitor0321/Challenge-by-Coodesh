package com.example.pharmainc.domain.repository.usecase

import androidx.lifecycle.LiveData
import com.example.pharmainc.data.db.entity.PatientEntity
import com.example.pharmainc.domain.error.type.ResultType
import com.example.pharmainc.domain.model.modelnetwork.Result

interface PatientRepositoryUseCase {

    suspend fun getResultApi(): ResultType<List<Result>>

    suspend fun addPatientDao(patientEntity: PatientEntity): ResultType<Long>

    suspend fun getAllPatientDao(): ResultType<LiveData<List<PatientEntity>>>
}