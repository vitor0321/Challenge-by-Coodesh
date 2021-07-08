package com.example.pharmainc.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pharmainc.R
import com.example.pharmainc.constants.TRUE_MENU
import com.example.pharmainc.data.RandomUserService
import com.example.pharmainc.data.RetrofitClient
import com.example.pharmainc.data.responseClient.CurrentPatientResponseApi
import com.example.pharmainc.databinding.FragmentHomeBinding
import com.example.pharmainc.ui.fragment.base.BaseFragment
import com.example.pharmainc.ui.model.ItemComponents
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE

class HomeFragment : BaseFragment() {

    private var _viewDataBinding: FragmentHomeBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    private val remote = RetrofitClient.createService(RandomUserService::class.java)

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
        randomUserService()
    }

    private fun randomUserService() {
        val call: Call<CurrentPatientResponseApi> = remote.list()
        val lst = call.enqueue(object : Callback<CurrentPatientResponseApi> {
            override fun onFailure(call: Call<CurrentPatientResponseApi>, t: Throwable) {
                val error = t.message
            }

            override fun onResponse(
                call: Call<CurrentPatientResponseApi>,
                response: Response<CurrentPatientResponseApi>
            ) {
                val count = response.body()
            }
        })
    }

    private fun initObserver() {
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