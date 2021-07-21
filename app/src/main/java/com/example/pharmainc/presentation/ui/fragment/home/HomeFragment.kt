package com.example.pharmainc.presentation.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmainc.R
import com.example.pharmainc.databinding.FragmentHomeBinding
import com.example.pharmainc.domain.model.ItemComponents
import com.example.pharmainc.domain.model.Patient
import com.example.pharmainc.presentation.constants.*
import com.example.pharmainc.presentation.dataBinding.data.PatientData
import com.example.pharmainc.presentation.ui.fragment.base.BaseFragment
import com.example.pharmainc.presentation.ui.fragment.bottomSheet.BottomSheetFragment
import com.example.photoday.ui.toast.Toast.toast
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE


class HomeFragment : BaseFragment() {

    private var _viewDataBinding: FragmentHomeBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    private val adapterHome: HomeAdapter by inject()
    private val viewModel: HomeViewModel by viewModel {
        parametersOf(findNavController())
    }
    private val itemPatientData: PatientData by inject {
        parametersOf(this)
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
        initRecycleView()
        initObserver()
    }

    private fun initRecycleView() {
        viewDataBinding.recyclerviewItemHome.run {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapterHome
            adapterHome.onItemClickListener = { itemPatient ->
                setDataPatient(itemPatient)
                navToBottomSheet()
            }
        }
    }

    private fun setDataPatient(itemPatient: Patient) {
        itemPatientData.setItemPatientData(itemPatient)
    }

    private fun navToBottomSheet() {
        activity?.let { activity ->
            BottomSheetFragment.newInstance().apply {
                listenerNote = this
            }
                .show(activity.supportFragmentManager, BOTTOM_SHEET)
        }
    }

    private fun initObserver() {
        viewModel.returnApiLiveData.observe(viewLifecycleOwner) { errorApi(it) }
    }

    private fun errorApi(responseApi: Pair<Int?, List<Patient>?>) {
        when (responseApi.first) {
            ERROR_400 -> toast(getString(R.string.error_api_400_generic))
            ERROR_401 -> toast(getString(R.string.error_api_401))
            ERROR_500 -> toast(getString(R.string.error_api_500_generic))
        }
        responseApi.second?.let { listPatient -> adapterHome.update(listPatient) }
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