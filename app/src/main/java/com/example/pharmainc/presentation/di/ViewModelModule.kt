package com.example.pharmainc.presentation.di

import com.example.pharmainc.domain.repository.usecase.PatientRepositoryUseCase
import com.example.pharmainc.presentation.dataBinding.data.ItemComponentsData
import com.example.pharmainc.presentation.ui.activity.PharmaViewModel
import com.example.pharmainc.presentation.ui.fragment.home.HomeViewModel
import com.example.pharmainc.presentation.usecase.ClickedCheckBoxUseCase
import com.example.pharmainc.presentation.usecase.SearchingNationalityUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<HomeViewModel> {
        HomeViewModel(
            get<ClickedCheckBoxUseCase>(),
            get<PatientRepositoryUseCase>(),
            get<SearchingNationalityUseCase>()
        )
    }
    viewModel<PharmaViewModel> { PharmaViewModel(get<ItemComponentsData>()) }
}