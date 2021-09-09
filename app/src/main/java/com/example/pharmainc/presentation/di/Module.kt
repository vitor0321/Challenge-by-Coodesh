package com.example.pharmainc.presentation.di

import androidx.fragment.app.FragmentActivity
import com.example.pharmainc.data.db.AppDataBase
import com.example.pharmainc.data.db.dao.PatientDao
import com.example.pharmainc.data.network.webservice.PatientApiDataSource
import com.example.pharmainc.data.network.webservice.PatientDataSource
import com.example.pharmainc.data.network.webservice.PatientRepository
import com.example.pharmainc.domain.mapper.ResultMapperUseCase
import com.example.pharmainc.domain.mapper.ResultMapperUseCaseImpl
import com.example.pharmainc.domain.usecase.dao.PatientDAOUseCase
import com.example.pharmainc.domain.usecase.dao.PatientDAOUseCaseImpl
import com.example.pharmainc.domain.usecase.network.GetResultUseCase
import com.example.pharmainc.domain.usecase.network.GetResultUseCaseImpl
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
import org.koin.android.ext.koin.androidContext
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
            getResultUseCase = get<GetResultUseCase>(),
            resultMapperUseCase = get<ResultMapperUseCase>(),
            searchingNationalityUseCase = get<SearchingNationalityUseCase>(),
            clickedCheckBoxUseCase = get<ClickedCheckBoxUseCase>(),
            patientDAOUseCase = get<PatientDAOUseCase>()
        )
    }
    viewModel<PharmaViewModel> { PharmaViewModel(get<ItemComponentsData>()) }
}

val useCaseModulo = module {
    single<ResultMapperUseCase> { ResultMapperUseCaseImpl() }
    single<ClickedCheckBoxUseCase> { ClickedCheckBoxUseCaseImpl(get<ItemCheckGenderData>()) }
    single<SearchingNationalityUseCase> { SearchingNationalityUseCaseImpl() }

    single<GetResultUseCase> { GetResultUseCaseImpl(get<PatientRepository>()) }
    single<PatientRepository> { PatientRepository(get<PatientDataSource>()) }
    single<PatientDataSource> { PatientApiDataSource() }
    factory<PatientApiDataSource> { PatientApiDataSource() }

    single<PatientDAOUseCase> { PatientDAOUseCaseImpl(get<PatientDao>()) }
}

val dbModule = module {
    factory { AppDataBase.getInstance(get()) }
}

val navModulo = module {
    single<Navigation> { Navigation }
}
val appModules = listOf(
    dataBindingModulo,
    uiModulo,
    viewModelModulo,
    useCaseModulo,
    dbModule,
    navModulo
)