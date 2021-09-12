package com.example.pharmainc.presentation.usecase

import com.example.pharmainc.presentation.model.Patient

interface SearchingNationalityUseCase {

    suspend fun searchingNationality(listPatient: List<Patient>, searching: String): List<Patient>
}