package com.example.core.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.data.repository.PatientRepository
import com.example.core.domain.model.Patient
import com.example.core.usecase.base.PagingUseCase
import kotlinx.coroutines.flow.Flow

class GetPatientUseCase(
    private val resultRepository: PatientRepository
) : PagingUseCase<GetPatientUseCase.GetCharactersParams, Patient>() {


    override fun createFlowObservable(params: GetCharactersParams): Flow<PagingData<Patient>> {
        return Pager(config = params.pagingConfig) {
            resultRepository.getPatient(params.query)
        }.flow
    }

    data class GetCharactersParams(val query: String, val pagingConfig: PagingConfig)
}