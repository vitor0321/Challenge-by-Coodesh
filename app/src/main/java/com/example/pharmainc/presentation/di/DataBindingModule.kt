package com.example.pharmainc.presentation.di

import com.example.pharmainc.presentation.dataBinding.data.ItemCheckGenderData
import com.example.pharmainc.presentation.dataBinding.data.ItemComponentsData
import com.example.pharmainc.presentation.dataBinding.data.PatientData
import org.koin.dsl.module

val dataBindingModule = module {
    single<PatientData> { PatientData() }
    single<ItemComponentsData> { ItemComponentsData() }
    single<ItemCheckGenderData> { ItemCheckGenderData() }
}