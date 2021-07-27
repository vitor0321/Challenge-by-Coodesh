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
import com.example.pharmainc.domain.eventBus.MessageEventGender
import com.example.pharmainc.domain.model.ItemComponents
import com.example.pharmainc.domain.model.Patient
import com.example.pharmainc.presentation.constants.*
import com.example.pharmainc.presentation.dataBinding.data.ItemCheckGenderData
import com.example.pharmainc.presentation.dataBinding.data.PatientData
import com.example.pharmainc.presentation.ui.fragment.base.BaseFragment
import com.example.pharmainc.presentation.bottomSheet.BottomSheetFragment
import com.example.photoday.ui.toast.Toast.toast
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE


class HomeFragment : BaseFragment() {

    private var _viewDataBinding: FragmentHomeBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    private lateinit var patient: Patient
    private var listPatient: MutableList<Patient> = mutableListOf()
    private val adapterHome: HomeAdapter by inject()
    private var itemCheckGenderData: ItemCheckGenderData? = null
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
        initEventBus()
        init()
    }

    private fun initEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
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

    private fun getPatientFromApi() = viewModel.getPatient()

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

    private fun setDataPatient() = itemPatientData.setItemPatientData(patient)

    private fun initObserver() {
        viewModel.returnApiLiveData.observe(viewLifecycleOwner) { responseApi(it) }
        viewModel.returnCheckBoxGender.observe(viewLifecycleOwner) { upDateAdapterListPatient(it) }

    }

    private fun responseApi(responseApi: Pair<Int?, List<Patient>?>) {
        setErrorApi(responseApi.first)
        responseApi.second?.let { listPatient ->
            listPatient.map {
                this.listPatient.add(it)
            }
            viewModel.checkBoxGender(this.listPatient)
            viewFlipperControl(CHILD_SECOND)
        }
    }

    private fun upDateAdapterListPatient(listPatient: List<Patient>) {
        adapterHome.update(listPatient)
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

    private fun setErrorApi(responseApi: Int?) {
        when (responseApi) {
            ERROR_400 -> setViews(getString(R.string.error_api_400_generic), View.VISIBLE, FALSE)
            ERROR_401 -> setViews(getString(R.string.error_api_401), View.VISIBLE, FALSE)
            ERROR_500 -> setViews(getString(R.string.error_api_500_generic), View.VISIBLE, FALSE)
            NULL -> setViews(NULL, View.GONE, TRUE)
        }
    }

    private fun setViews(toast: String?, messageError: Int?, statusBar: Boolean?) {
        toast?.let { toast(toast) }
        messageError?.let { messageError(messageError) }
        statusBar?.let { statusBarNavigation(statusBar) }
    }

    private fun viewFlipperControl(child: Int) {
        when (child) {
            CHILD_FIRST -> {
                setViews(NULL, View.GONE, TRUE)
                viewDataBinding.viewFlipperHome.displayedChild = CHILD_FIRST
            }
            CHILD_SECOND -> {
                setViews(NULL, View.GONE, TRUE)
                Handler(Looper.getMainLooper()).postDelayed({
                    viewDataBinding.viewFlipperHome.displayedChild = CHILD_SECOND
                }, LOADING_TIME_OUT)
            }
        }
    }

    private fun onScrollListener(recyclerView: RecyclerView) {
        viewDataBinding.recyclerviewItemHome.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recycler: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount: Int =
                    (recyclerView.layoutManager as LinearLayoutManager).childCount
                val totalItemCount: Int =
                    (recyclerView.layoutManager as LinearLayoutManager).itemCount
                val pastVisibleItems: Int =
                    (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                when {
                    dy > 0 -> {
                        when {
                            visibleItemCount + pastVisibleItems >= totalItemCount -> {
                                loadingRecycle(View.VISIBLE)
                                Handler(Looper.getMainLooper()).postDelayed({
                                    getPatientFromApi()
                                    loadingRecycle(View.GONE)
                                }, LOADING_TIME_OUT)
                            }
                        }
                    }
                }
            }
        })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(checkGender: MessageEventGender) {
        itemCheckGenderData?.setCheckGenderData(checkGender.message)
        viewModel.checkBoxGender(this.listPatient)
    }

    private fun navToBottomSheet() {
        activity?.let { activity ->
            BottomSheetFragment.newInstance().apply {
                listenerSheet = this
            }
                .show(activity.supportFragmentManager, BOTTOM_SHEET)
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

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        this._viewDataBinding = null
    }
}