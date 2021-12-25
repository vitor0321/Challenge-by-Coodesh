package com.example.core.usecase.base

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

abstract class UseCase<in P, R> {

    operator fun invoke(params: P): Flow<PatientStatus<R>> = flow {
        emit(PatientStatus.Loading)
        emit(doWork(params))
    }.catch { throwable ->
        emit(PatientStatus.Error(throwable))
    }

    protected abstract suspend fun doWork(params: P): PatientStatus<R>
}

abstract class PagingUseCase<in P, R : Any> {

    operator fun invoke(params: P): Flow<PagingData<R>> = createFlowObservable(params)

    protected abstract fun createFlowObservable(params: P): Flow<PagingData<R>>
}