package com.example.pharmainc.presentation.di

import com.example.pharmainc.presentation.navigation.Navigation
import org.koin.dsl.module

val navModule = module {
    single<Navigation> { Navigation }
}