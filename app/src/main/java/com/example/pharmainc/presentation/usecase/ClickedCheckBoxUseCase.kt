package com.example.pharmainc.presentation.usecase

import com.example.core.domain.model.Patient

interface ClickedCheckBoxUseCase {

    suspend fun onClickedCheckBox(listPatient: List<Patient>): List<Patient>
}