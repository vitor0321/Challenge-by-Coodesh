package com.example.pharmainc.ui.navigation

import androidx.navigation.NavController
import com.example.pharmainc.ui.fragment.splash.SplashFragmentDirections

object Navigation {
    fun navFragmentSplashToHome(navFragment: NavController) {
        SplashFragmentDirections.actionNavSplashFragmentToNavHomeFragment()
            .let(navFragment::navigate)
    }
}