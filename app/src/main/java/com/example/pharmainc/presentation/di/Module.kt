package com.example.pharmainc.presentation.di

import androidx.fragment.app.FragmentActivity
import com.example.pharmainc.data.repository.PatientApiDataSource
import com.example.pharmainc.data.repository.PatientDataSource
import com.example.pharmainc.data.repository.PatientRepository
import com.example.pharmainc.domain.mapper.ResultNetworkMapper
import com.example.pharmainc.domain.usecase.GetPatientUseCase
import com.example.pharmainc.domain.usecase.GetPatientUseCaseImpl
import com.example.pharmainc.presentation.dataBinding.data.ItemCheckGenderData
import com.example.pharmainc.presentation.dataBinding.data.ItemComponentsData
import com.example.pharmainc.presentation.dataBinding.data.PatientData
import com.example.pharmainc.presentation.navigation.Navigation
import com.example.pharmainc.presentation.ui.activity.PharmaActivity
import com.example.pharmainc.presentation.ui.activity.PharmaViewModel
import com.example.pharmainc.presentation.ui.dialog.GenderDialog
import com.example.pharmainc.presentation.ui.fragment.base.BaseFragment
import com.example.pharmainc.presentation.ui.fragment.home.HomeAdapter
import com.example.pharmainc.presentation.ui.fragment.home.HomeFragment
import com.example.pharmainc.presentation.ui.fragment.home.HomeViewModel
import com.example.pharmainc.presentation.usecase.ClickedCheckBoxUseCase
import com.example.pharmainc.presentation.usecase.ClickedCheckBoxUseCaseImpl
import com.example.pharmainc.presentation.usecase.SearchingNationalityUseCase
import com.example.pharmainc.presentation.usecase.SearchingNationalityUseCaseImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataBindingModulo = module {
    single<ItemComponentsData> { ItemComponentsData() }
    single<PatientData> { PatientData() }
    single<ItemCheckGenderData> { ItemCheckGenderData() }
}

val uiModulo = module {
    factory<FragmentActivity> { FragmentActivity() }
    factory<PharmaActivity> { PharmaActivity() }
    factory<HomeFragment> { HomeFragment() }
    factory<GenderDialog> { GenderDialog() }
    factory<BaseFragment> { BaseFragment() }
}

val viewModelModulo = module {
    viewModel<PharmaViewModel> { PharmaViewModel() }
    viewModel<HomeViewModel> {
        HomeViewModel(
            getPatientUseCase = get<GetPatientUseCase>(),
            mapper = get<ResultNetworkMapper>(),
            searchingNationality = get<SearchingNationalityUseCase>(),
            clickedCheckBox = get<ClickedCheckBoxUseCase>()
        )
    }
}

val adapterModulo = module {
    factory<HomeAdapter> { HomeAdapter() }
}

val apiSourceModulo = module {
    factory<PatientApiDataSource> { PatientApiDataSource() }
}

val dataSourceModulo = module {
    single<PatientDataSource> { PatientApiDataSource() }
}

val repositoryModulo = module {
    single<PatientRepository> { PatientRepository(get<PatientDataSource>()) }
}

val useCaseModulo = module {
    single<GetPatientUseCase> { GetPatientUseCaseImpl(get<PatientRepository>()) }
    single<ClickedCheckBoxUseCase> { ClickedCheckBoxUseCaseImpl(get<ItemCheckGenderData>()) }
    single<SearchingNationalityUseCase> { SearchingNationalityUseCaseImpl() }
}

val mapperModulo = module {
    single<ResultNetworkMapper> { ResultNetworkMapper() }
    single<Navigation> { Navigation }
}
val appModules = listOf(
    dataBindingModulo,
    uiModulo,
    viewModelModulo,
    adapterModulo,
    apiSourceModulo,
    dataSourceModulo,
    repositoryModulo,
    useCaseModulo,
    mapperModulo
)