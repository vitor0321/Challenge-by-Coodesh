package com.example.pharmainc.di

import androidx.fragment.app.FragmentActivity
import com.example.pharmainc.ui.activity.PharmaActivity
import com.example.pharmainc.ui.activity.PharmaViewModel
import com.example.pharmainc.ui.dataBinding.ItemComponentsData
import com.example.pharmainc.ui.dataBinding.ItemPatientData
import com.example.pharmainc.ui.fragment.home.HomeFragment
import com.example.pharmainc.ui.fragment.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val dataBindingModulo = module(override = true) {
    single<ItemComponentsData> { ItemComponentsData() }
    single<ItemPatientData> { ItemPatientData() }
}

val uiModulo = module(override = true) {
    factory<FragmentActivity> { FragmentActivity() }
    factory<PharmaActivity> { PharmaActivity() }
    factory<HomeFragment> { HomeFragment() }
}

val viewModelModulo = module(override = true) {
    viewModel<PharmaViewModel> { PharmaViewModel() }
    viewModel<HomeViewModel> { HomeViewModel() }
}
val appModules = listOf(
    dataBindingModulo,
    uiModulo,
    viewModelModulo,
)
