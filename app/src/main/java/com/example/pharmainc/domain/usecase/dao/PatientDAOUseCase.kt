package com.example.pharmainc.domain.usecase.dao

import androidx.lifecycle.LiveData
import com.example.pharmainc.data.db.entity.PatientEntity

interface PatientDAOUseCase {

    suspend fun insertPatient(patient: PatientEntity): Long

    suspend fun getAllSubscriber(): LiveData<List<PatientEntity>>
}