package com.example.pharmainc.presentation.di

import com.example.pharmainc.data.db.PatientDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val daoModule = module {
    single { PatientDatabase.getInstance(androidContext()).patientDao }
}