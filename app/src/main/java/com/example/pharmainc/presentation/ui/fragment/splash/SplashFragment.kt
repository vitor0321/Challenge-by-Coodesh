package com.example.pharmainc.presentation.ui.fragment.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pharmainc.R
import com.example.pharmainc.databinding.FragmentSplashBinding
import com.example.pharmainc.presentation.constants.CHILD_FIRST_SPLASH
import com.example.pharmainc.presentation.constants.CHILD_SECOND_SPLASH
import com.example.pharmainc.presentation.constants.SPLASH_TIME_OUT
import com.example.pharmainc.presentation.constants.SPLASH_TIME_OUT_SECOND
import com.example.pharmainc.presentation.constants.FALSE_MENU
import com.example.pharmainc.presentation.navigation.Navigation
import com.example.pharmainc.presentation.ui.fragment.base.BaseFragment
import org.koin.android.ext.android.inject
import java.lang.Boolean.FALSE

class SplashFragment : BaseFragment() {

    private var _viewDataBinding: FragmentSplashBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    private val controlNavigation by lazy { findNavController() }
    private val navigation: Navigation by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewDataBinding = FragmentSplashBinding.inflate(inflater, container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        viewFlipperControl(CHILD_FIRST_SPLASH)
        statusBarNavigation()
        controlTimeNavigation()
    }

    private fun controlTimeNavigation() {
        Handler(Looper.getMainLooper()).postDelayed({
            viewFlipperControl(CHILD_SECOND_SPLASH)
        }, SPLASH_TIME_OUT)
        Handler(Looper.getMainLooper()).postDelayed({
            navigation.navFragmentSplashToHome(controlNavigation)
        }, SPLASH_TIME_OUT_SECOND)
    }

    private fun viewFlipperControl(child: Int) {
        when (child) {
            CHILD_FIRST_SPLASH -> viewDataBinding.run {
                viewFlipperSplash.displayedChild = CHILD_FIRST_SPLASH
            }
            CHILD_SECOND_SPLASH -> viewDataBinding.run {
                viewFlipperSplash.displayedChild = CHILD_SECOND_SPLASH
            }
        }
    }

    private fun statusBarNavigation() {
        statusAppBarNavigationBase(
            appBar = FALSE,
            bottomNavigation = FALSE,
            menu = FALSE_MENU,
            barColor = R.color.white
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewDataBinding = null
    }
}

