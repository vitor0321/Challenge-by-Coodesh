package com.example.core.data.repository

import com.example.core.domain.error.type.ResultType
import com.example.core.domain.model.Patient

interface ResultNetworkUseCase {

    suspend operator fun invoke(): ResultType<List<Patient>>
}