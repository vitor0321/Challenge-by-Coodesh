package com.example.pharmainc.domain.mapper.network

import com.example.pharmainc.domain.model.modelnetwork.Result
import com.example.core.domain.model.Patient

interface ResultMapperUseCase {

    fun fromEntityApiList(initial: List<Result>): List<Patient>
}