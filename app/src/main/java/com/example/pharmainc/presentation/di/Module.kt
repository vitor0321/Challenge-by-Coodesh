package com.example.pharmainc.presentation.di

import androidx.fragment.app.FragmentActivity
import com.example.pharmainc.common.viewModel.Dispatcher
import com.example.pharmainc.data.repository.PatientApiDataSource
import com.example.pharmainc.data.repository.PatientDataSource
import com.example.pharmainc.data.repository.PatientRepository
import com.example.pharmainc.domain.mapper.ResultMapperUseCase
import com.example.pharmainc.domain.mapper.ResultMapperUseCaseImpl
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
import com.example.pharmainc.presentation.ui.fragment.home.PatientHandler
import com.example.pharmainc.presentation.ui.fragment.home.data.PatientsDataDispatcher
import com.example.pharmainc.presentation.ui.fragment.home.view.HomeAdapter
import com.example.pharmainc.presentation.ui.fragment.home.view.HomeFragment
import com.example.pharmainc.presentation.ui.fragment.home.viewModel.HomeViewModel
import com.example.pharmainc.presentation.usecase.ClickedCheckBoxUseCase
import com.example.pharmainc.presentation.usecase.ClickedCheckBoxUseCaseImpl
import com.example.pharmainc.presentation.usecase.SearchingNationalityUseCase
import com.example.pharmainc.presentation.usecase.SearchingNationalityUseCaseImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataBindingModulo = module {
    single<PatientData> { PatientData() }
    single<ItemComponentsData> { ItemComponentsData() }
    single<ItemCheckGenderData> { ItemCheckGenderData() }
}

val uiModulo = module {
    factory<HomeAdapter> { HomeAdapter() }
    factory<HomeFragment> { HomeFragment() }
    factory<GenderDialog> { GenderDialog() }
    factory<BaseFragment> { BaseFragment() }
    factory<PharmaActivity> { PharmaActivity() }
    factory<FragmentActivity> { FragmentActivity() }
}

val viewModelModulo = module {
    viewModel<HomeViewModel> {
        HomeViewModel(
            getPatientUseCase = get<GetPatientUseCase>(),
            mapper = get<ResultMapperUseCase>(),
            searchingNationality = get<SearchingNationalityUseCase>(),
            clickedCheckBox = get<ClickedCheckBoxUseCase>()
        )
    }
    viewModel<PharmaViewModel> { PharmaViewModel(get<ItemComponentsData>()) }
}

val dispatcherModulo = module {
}

val useCaseModulo = module {
    single<GetPatientUseCase> { GetPatientUseCaseImpl(get<PatientRepository>()) }
    single<ResultMapperUseCase> { ResultMapperUseCaseImpl() }
    single<ClickedCheckBoxUseCase> { ClickedCheckBoxUseCaseImpl(get<ItemCheckGenderData>()) }
    single<SearchingNationalityUseCase> { SearchingNationalityUseCaseImpl() }

    single<PatientDataSource> { PatientApiDataSource() }
    single<PatientRepository> { PatientRepository(get<PatientDataSource>()) }
    factory<PatientApiDataSource> { PatientApiDataSource() }
}

val navModulo = module {
    single<Navigation> { Navigation }
}
val appModules = listOf(
    dataBindingModulo,
    uiModulo,
    viewModelModulo,
    dispatcherModulo,
    useCaseModulo,
    navModulo
)