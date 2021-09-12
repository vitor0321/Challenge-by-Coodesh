package com.example.pharmainc.presentation.di

import com.example.pharmainc.data.network.usecase.GetResultUseCase
import com.example.pharmainc.data.network.usecase.GetResultUseCaseImpl
import com.example.pharmainc.data.network.webservice.GeneralErrorHandlerImpl
import com.example.pharmainc.data.network.webservice.PatientRepository
import com.example.pharmainc.data.network.webservice.ResultApiDataSource
import com.example.pharmainc.data.network.webservice.ResultDataSource
import com.example.pharmainc.domain.error.ErrorHandler
import com.example.pharmainc.domain.mapper.ResultMapperUseCase
import com.example.pharmainc.domain.mapper.ResultMapperUseCaseImpl
import com.example.pharmainc.domain.repository.usecase.PatientRepositoryUseCase
import com.example.pharmainc.domain.repository.usecase.PatientRepositoryUseCaseImpl
import com.example.pharmainc.presentation.dataBinding.data.ItemCheckGenderData
import com.example.pharmainc.presentation.usecase.ClickedCheckBoxUseCase
import com.example.pharmainc.presentation.usecase.ClickedCheckBoxUseCaseImpl
import com.example.pharmainc.presentation.usecase.SearchingNationalityUseCase
import com.example.pharmainc.presentation.usecase.SearchingNationalityUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {
    single<ErrorHandler> { GeneralErrorHandlerImpl() }
    single<SearchingNationalityUseCase> { SearchingNationalityUseCaseImpl() }
    single<ClickedCheckBoxUseCase> { ClickedCheckBoxUseCaseImpl(get<ItemCheckGenderData>()) }

    single<GetResultUseCase> { GetResultUseCaseImpl(get<PatientRepository>(), get<ErrorHandler>()) }
    factory<ResultApiDataSource> { ResultApiDataSource() }
    single<ResultDataSource> { ResultApiDataSource() }

    single<ResultMapperUseCase> { ResultMapperUseCaseImpl() }

    single<PatientRepository> { PatientRepository(get<ResultDataSource>()) }
    single<PatientRepositoryUseCase> { PatientRepositoryUseCaseImpl(get<GetResultUseCase>()) }
}


