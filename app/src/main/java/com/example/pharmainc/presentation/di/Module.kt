package com.example.pharmainc.presentation.di

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.example.pharmainc.network.mapper.ResultNetworkMapper
import com.example.pharmainc.network.repository.PatientApiDataSource
import com.example.pharmainc.presentation.dataBinding.data.ItemCheckGenderData
import com.example.pharmainc.presentation.dataBinding.data.ItemComponentsData
import com.example.pharmainc.presentation.dataBinding.data.PatientData
import com.example.pharmainc.presentation.navigation.Navigation
import com.example.pharmainc.presentation.ui.activity.PharmaActivity
import com.example.pharmainc.presentation.ui.activity.PharmaViewModel
import com.example.pharmainc.presentation.dialog.GenderDialog
import com.example.pharmainc.presentation.ui.fragment.home.HomeAdapter
import com.example.pharmainc.presentation.ui.fragment.home.HomeFragment
import com.example.pharmainc.presentation.ui.fragment.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataBindingModulo = module(override = true) {
    single<ItemComponentsData> { ItemComponentsData() }
    single<PatientData> { PatientData() }
    single<ItemCheckGenderData> { ItemCheckGenderData() }
}

val uiModulo = module(override = true) {
    factory<FragmentActivity> { FragmentActivity() }
    factory<PharmaActivity> { PharmaActivity() }
    factory<HomeFragment> { HomeFragment() }
    factory<GenderDialog> { GenderDialog() }
}

val viewModelModulo = module(override = true) {
    viewModel<PharmaViewModel> { PharmaViewModel() }
    viewModel<HomeViewModel> {
        HomeViewModel(
            apiRepository = get<PatientApiDataSource>(),
            mapper = get<ResultNetworkMapper>(),
            checkGenderData = get<ItemCheckGenderData>()
        )
    }
}

val adapterModulo = module(override = true) {
    factory<HomeAdapter> { HomeAdapter(get<Context>()) }
}

val apiSourceModulo = module(override = true) {
    factory<PatientApiDataSource> { PatientApiDataSource() }
}

val mapperModulo = module(override = true) {
    single<ResultNetworkMapper> { ResultNetworkMapper() }
    single<Navigation> { Navigation }
}
val appModules = listOf(
    dataBindingModulo,
    uiModulo,
    viewModelModulo,
    adapterModulo,
    apiSourceModulo,
    mapperModulo
)
