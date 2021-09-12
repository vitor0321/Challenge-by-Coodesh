package com.example.pharmainc.presentation.di

import androidx.fragment.app.FragmentActivity
import com.example.pharmainc.presentation.ui.activity.PharmaActivity
import com.example.pharmainc.presentation.ui.dialog.GenderDialog
import com.example.pharmainc.presentation.ui.fragment.base.BaseFragment
import com.example.pharmainc.presentation.ui.fragment.home.HomeAdapter
import com.example.pharmainc.presentation.ui.fragment.home.HomeFragment
import org.koin.dsl.module

val uiModule = module {
    factory<HomeAdapter> { HomeAdapter() }
    factory<HomeFragment> { HomeFragment() }
    factory<GenderDialog> { GenderDialog() }
    factory<BaseFragment> { BaseFragment() }
    factory<PharmaActivity> { PharmaActivity() }
    factory<FragmentActivity> { FragmentActivity() }
}