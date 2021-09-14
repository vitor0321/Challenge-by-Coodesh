package com.example.pharmainc.domain.mapper.dao

import com.example.pharmainc.data.db.entity.PatientEntity
import com.example.pharmainc.presentation.model.Patient

interface PatientEntityMapperUseCase {

    fun fromEntityDaoList(initial: List<PatientEntity>): List<Patient>
}