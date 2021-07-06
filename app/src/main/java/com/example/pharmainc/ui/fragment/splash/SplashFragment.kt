package com.example.pharmainc.ui.fragment.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pharmainc.R
import com.example.pharmainc.constants.*
import com.example.pharmainc.databinding.FragmentSplashBinding
import com.example.pharmainc.ui.fragment.base.BaseFragment
import com.example.pharmainc.ui.model.ItemComponents
import com.example.pharmainc.ui.navigation.Navigation.navFragmentSplashToHome
import java.lang.Boolean.FALSE

class SplashFragment : BaseFragment() {

    private var _viewDataBinding: FragmentSplashBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    private val controlNavigation by lazy { findNavController() }

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
        viewFlipperControl(CHILD_FIRST)
        statusBarNavigation()
        Handler(Looper.getMainLooper()).postDelayed({
            viewFlipperControl(CHILD_SECOND)
        }, SPLASH_TIME_OUT)
        Handler(Looper.getMainLooper()).postDelayed({
            navFragmentSplashToHome(controlNavigation)
            onDestroy()
        }, SPLASH_TIME_OUT_SECOND)
    }

    private fun viewFlipperControl(child: Int) {
        when (child) {
            CHILD_FIRST -> viewDataBinding.run { viewFlipperSplash.displayedChild = CHILD_FIRST }
            CHILD_SECOND -> viewDataBinding.run { viewFlipperSplash.displayedChild = CHILD_SECOND }
        }
    }

    private fun statusBarNavigation() {
        statusAppBarNavigationBase(
            menu = FALSE_MENU,
            components = ItemComponents(
                appBar = FALSE,
                bottomNavigation = FALSE,
                actionBar = FALSE
            ),
            barColor = R.color.white
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewDataBinding = null
    }

}