package com.example.pharmainc.domain.usecase

import com.example.pharmainc.domain.model.modelnetworl.Result

interface GetPatientUseCase {
    suspend operator fun invoke(): List<Result>
}