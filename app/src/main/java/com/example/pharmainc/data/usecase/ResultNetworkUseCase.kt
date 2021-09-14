package com.example.pharmainc.data.usecase

import com.example.pharmainc.domain.model.modelnetwork.Result
import com.example.pharmainc.domain.error.type.ResultType

interface ResultNetworkUseCase {

    suspend operator fun invoke(): ResultType<List<Result>>
}