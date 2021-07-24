package com.example.pharmainc.presentation.ui.fragment.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private lateinit var patient: Patient
    private lateinit var listPatient: List<Patient>
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

    override fun onResume() {
        super.onResume()
        viewFlipperControl(CHILD_FIRST)
    }

    private fun init() {
        statusBarNavigation(TRUE)
        initRecycleView()
        initObserver()
        getPatientFromApi()
    }

    private fun getPatientFromApi() {
        viewModel.getPatient()
    }

    private fun initRecycleView() {
        viewDataBinding.apply {
            recyclerviewItemHome.run {
                layoutManager = LinearLayoutManager(context)
                this.adapter = adapterHome
                adapterHome.onItemClickListener = { itemPatient ->
                    patient = itemPatient
                    setDataPatient()
                    navToBottomSheet()
                }
                onScrollListener(this)
            }
        }
    }

    private fun onScrollListener(recyclerView1: RecyclerView) {
        viewDataBinding.recyclerviewItemHome.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val visibleItemCount: Int =
                        (recyclerView1.layoutManager as LinearLayoutManager).childCount
                    val totalItemCount: Int =
                        (recyclerView1.layoutManager as LinearLayoutManager).itemCount
                    val pastVisibleItems: Int =
                        (recyclerView1.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                        loadingRecycle(View.VISIBLE)
                        Handler(Looper.getMainLooper()).postDelayed({
                            getPatientFromApi()
                            loadingRecycle(View.GONE)
                        }, LOADING_TIME_OUT)
                    }
                }
            }
        })
    }

    private fun setDataPatient() {
        itemPatientData.setItemPatientData(patient)
    }

    private fun initObserver() {
        viewModel.returnApiLiveData.observe(viewLifecycleOwner) { responseApi(it) }
    }

    private fun responseApi(responseApi: Pair<Int?, List<Patient>?>) {
        setErrorApi(responseApi.first)
        responseApi.second?.let { listPatient ->
            setListPatient(listPatient)
            upDateAdapterListPatient()
            viewFlipperControl(CHILD_SECOND)
        }
    }

    private fun setErrorApi(responseApi: Int?) {
        when (responseApi) {
            ERROR_400 -> {
                toast(getString(R.string.error_api_400_generic))
                messageError(View.VISIBLE)
                statusBarNavigation(FALSE)
            }
            ERROR_401 -> {
                toast(getString(R.string.error_api_401))
                messageError(View.VISIBLE)
                statusBarNavigation(FALSE)
            }
            ERROR_500 -> {
                toast(getString(R.string.error_api_500_generic))
                messageError(View.VISIBLE)
                statusBarNavigation(FALSE)
            }
        }
    }

    private fun messageError(status: Int) {
        when (status) {
            View.VISIBLE -> viewDataBinding.errorMessage.visibility = View.VISIBLE
            View.GONE -> viewDataBinding.errorMessage.visibility = View.GONE
        }
    }

    private fun loadingRecycle(status: Int) {
        when (status) {
            View.VISIBLE -> viewDataBinding.loadingViewRecycler.visibility = View.VISIBLE
            View.GONE -> viewDataBinding.loadingViewRecycler.visibility = View.GONE
        }
    }

    private fun navToBottomSheet() {
        activity?.let { activity ->
            BottomSheetFragment.newInstance().apply {
                listenerSheet = this
            }
                .show(activity.supportFragmentManager, BOTTOM_SHEET)
        }
    }

    @JvmName("setListPatient1")
    private fun setListPatient(listPatient: List<Patient>) {
        this.listPatient = listPatient
    }

    private fun upDateAdapterListPatient() {
        adapterHome.update(listPatient)
    }

    private fun viewFlipperControl(child: Int) {
        when (child) {
            CHILD_FIRST -> {
                messageError(View.GONE)
                viewDataBinding.viewFlipperHome.displayedChild = CHILD_FIRST
            }
            CHILD_SECOND -> {
                messageError(View.GONE)
                statusBarNavigation(TRUE)
                Handler(Looper.getMainLooper()).postDelayed({
                    viewDataBinding.viewFlipperHome.displayedChild = CHILD_SECOND
                }, LOADING_TIME_OUT)
            }
        }
    }

    private fun statusBarNavigation(bottom: Boolean) {
        statusAppBarNavigationBase(
            menu = TRUE_MENU,
            components = ItemComponents(
                appBar = bottom,
                bottomNavigation = bottom,
                actionBar = FALSE
            ),
            barColor = R.color.light_blue
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        this._viewDataBinding = null
    }
}