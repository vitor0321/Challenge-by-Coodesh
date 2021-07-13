package com.example.pharmainc.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pharmainc.R
import com.example.pharmainc.constants.ERROR_400
import com.example.pharmainc.constants.ERROR_401
import com.example.pharmainc.constants.ERROR_500
import com.example.pharmainc.constants.TRUE_MENU
import com.example.pharmainc.databinding.FragmentHomeBinding
import com.example.pharmainc.ui.fragment.base.BaseFragment
import com.example.pharmainc.ui.model.ItemComponents
import com.example.photoday.ui.toast.Toast.toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE

class HomeFragment : BaseFragment() {

    private var _viewDataBinding: FragmentHomeBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    private val viewModel: HomeViewModel by viewModel {
        parametersOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewDataBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return viewDataBinding.root
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init() {
        statusBarNavigation()
        initObserver()
    }

    private fun initObserver() {
        viewModel.errorLiveData.observe(viewLifecycleOwner) { errorApi(it) }
    }

    private fun errorApi(error: Int?) {
        when (error) {
            ERROR_400 -> toast(getString(R.string.error_api_400_generic))
            ERROR_401 -> toast(getString(R.string.error_api_401))
            ERROR_500 -> toast(getString(R.string.error_api_500_generic))
        }
    }

    private fun statusBarNavigation() {
        statusAppBarNavigationBase(
            menu = TRUE_MENU,
            components = ItemComponents(appBar = TRUE, bottomNavigation = TRUE, actionBar = FALSE),
            barColor = R.color.light_blue
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        this._viewDataBinding = null
    }
}