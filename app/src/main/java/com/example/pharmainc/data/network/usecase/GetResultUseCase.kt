package com.example.pharmainc.data.network.usecase

import com.example.pharmainc.domain.model.modelnetwork.Result
import com.example.pharmainc.domain.error.type.ResultType

interface GetResultUseCase {

    suspend operator fun invoke(): ResultType<List<Result>>
}