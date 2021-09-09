package com.example.pharmainc.presentation.usecase

import com.example.pharmainc.data.db.entity.PatientEntity

interface ClickedCheckBoxUseCase {

    suspend fun onClickedCheckBox(listPatient: List<PatientEntity>): List<PatientEntity>
}