package com.example.pharmainc.presentation.navigation

import androidx.navigation.NavController
import com.example.pharmainc.presentation.ui.fragment.splash.SplashFragmentDirections

object Navigation {
    fun navFragmentSplashToHome(navFragment: NavController) {
        SplashFragmentDirections.actionNavSplashFragmentToNavHomeFragment()
            .let(navFragment::navigate)
    }
}