package com.example.pharmainc.domain.mapper.dao

import com.example.pharmainc.data.db.entity.PatientEntity
import com.example.pharmainc.presentation.model.Patient

interface EntityPatientMapperUseCase {

    fun fromViewList(initial: List<PatientEntity>): List<Patient>
}