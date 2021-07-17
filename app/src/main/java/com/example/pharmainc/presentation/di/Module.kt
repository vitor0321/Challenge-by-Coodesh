package com.example.pharmainc.presentation.di

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.example.pharmainc.network.mapper.ResultNetworkMapper
import com.example.pharmainc.network.repository.PatientApiDataSource
import com.example.pharmainc.presentation.dataBinding.data.ItemComponentsData
import com.example.pharmainc.presentation.dataBinding.data.PatientData
import com.example.pharmainc.presentation.ui.activity.PharmaActivity
import com.example.pharmainc.presentation.ui.activity.PharmaViewModel
import com.example.pharmainc.presentation.ui.fragment.home.HomeAdapter
import com.example.pharmainc.presentation.ui.fragment.home.HomeFragment
import com.example.pharmainc.presentation.ui.fragment.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val dataBindingModulo = module(override = true) {
    single<ItemComponentsData> { ItemComponentsData() }
    single<PatientData> { PatientData() }
}

val uiModulo = module(override = true) {
    factory<FragmentActivity> { FragmentActivity() }
    factory<PharmaActivity> { PharmaActivity() }
    factory<HomeFragment> { HomeFragment() }
}

val viewModelModulo = module(override = true) {
    viewModel<PharmaViewModel> { PharmaViewModel() }
    viewModel<HomeViewModel> {
        HomeViewModel(
            get<PatientApiDataSource>(),
            get<ResultNetworkMapper>()
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
}
val appModules = listOf(
    dataBindingModulo,
    uiModulo,
    viewModelModulo,
    adapterModulo,
    apiSourceModulo,
    mapperModulo
)
