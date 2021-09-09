package com.example.pharmainc.domain.usecase.network

import com.example.pharmainc.domain.model.modelnetworl.Result

interface GetResultUseCase {
    suspend operator fun invoke(): List<Result>
}