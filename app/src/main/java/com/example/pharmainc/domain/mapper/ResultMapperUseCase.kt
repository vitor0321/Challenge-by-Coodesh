package com.example.pharmainc.domain.mapper

import com.example.pharmainc.domain.model.modelnetworl.Result
import com.example.pharmainc.data.db.entity.PatientEntity

interface ResultMapperUseCase {

    fun fromEntityApiList(initial: List<Result>): List<PatientEntity>
}