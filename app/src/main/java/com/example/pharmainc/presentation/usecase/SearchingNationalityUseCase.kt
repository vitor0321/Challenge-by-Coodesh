package com.example.pharmainc.presentation.usecase

import com.example.pharmainc.data.db.entity.PatientEntity

interface SearchingNationalityUseCase {

    suspend fun searchingNationality(listPatient: List<PatientEntity>, searching: String): List<PatientEntity>
}