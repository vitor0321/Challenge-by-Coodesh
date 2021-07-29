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
import com.example.pharmainc.domain.eventBus.MessageEventSearch
import com.example.pharmainc.domain.model.ItemComponents
import com.example.pharmainc.domain.model.Patient
import com.example.pharmainc.presentation.constants.*
import com.example.pharmainc.presentation.dataBinding.data.ItemCheckGenderData
import com.example.pharmainc.presentation.dataBinding.data.PatientData
import com.example.pharmainc.presentation.ui.bottomSheet.BottomSheetFragment
import com.example.pharmainc.presentation.ui.fragment.base.BaseFragment
import com.example.photoday.ui.toast.Toast.toast
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE
import java.util.*

class HomeFragment : BaseFragment() {

    private var _viewDataBinding: FragmentHomeBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    private lateinit var patient: Patient
    private var searchingNat: String = EMPTY
    private var itemCheckGenderData: ItemCheckGenderData? = null
    private var listPatient: MutableList<Patient> = mutableListOf()
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
        CHILD_FIRST.viewFlipperControl()
    }

    private fun init() {
        TRUE.statusBarNavigation()
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
                onScrollListener()
            }
        }
    }

    private fun setDataPatient() = itemPatientData.setItemPatientData(patient)

    private fun initObserver() {
        viewModel.apiLiveData.observe(viewLifecycleOwner) { it.responseApi() }
        viewModel.checkBoxGenderLiveData.observe(viewLifecycleOwner) { it.upDateAdapterListPatient() }
        viewModel.filterLiveData.observe(viewLifecycleOwner) { it.upDateFilterList() }
        //viewModel.loadingLiveData.observe(viewLifecycleOwner) { it.loadingRecycle() }
    }

    private fun Pair<Int?, List<Patient>?>.responseApi() {
        setErrorApi(this.first)
        this.second?.let { listPatient ->
            listPatient.map {
                this@HomeFragment.listPatient.add(it)
            }
            viewModel.checkBoxGender(this@HomeFragment.listPatient)
            CHILD_SECOND.viewFlipperControl()
        }
    }

    private fun List<Patient>.upDateAdapterListPatient() {
        adapterHome.update(this)
    }

    private fun List<Patient>.upDateFilterList() {
        val patientList: MutableList<Patient> = mutableListOf()
        this.map {
            patientList.add(it)
        }
        adapterHome.filterList(patientList)
    }

    private fun Int.viewError() {
        when (this) {
            View.VISIBLE -> viewDataBinding.errorMessage.visibility = View.VISIBLE
            View.GONE -> viewDataBinding.errorMessage.visibility = View.GONE
        }
    }

    private fun Int.loadingRecycle() {
        when (this) {
            View.VISIBLE -> viewDataBinding.loadingViewRecycler.visibility = View.VISIBLE
            View.GONE -> viewDataBinding.loadingViewRecycler.visibility = View.GONE
        }
    }

    private fun setErrorApi(responseApi: Int?) {
        when (responseApi) {
            ERROR_400 -> setViews(R.string.error_api_400_generic, View.VISIBLE, FALSE)
            ERROR_401 -> setViews(R.string.error_api_401, View.VISIBLE, FALSE)
            ERROR_500 -> setViews(R.string.error_api_500_generic, View.VISIBLE, FALSE)
            NULL -> setViews(NULL, View.GONE, TRUE)
        }
    }

    private fun setViews(messageError: Int?, viewError: Int?, statusBar: Boolean?) {
        messageError?.let { messageError.messageToast() }
        viewError?.let { viewError.viewError() }
        statusBar?.let { statusBar.statusBarNavigation() }
    }

    private fun Int.viewFlipperControl() {
        when (this) {
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

    private fun onScrollListener() {
        viewDataBinding.recyclerviewItemHome.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recycler: RecyclerView, dx: Int, dy: Int) {
                when (searchingNat) {
                    EMPTY -> recycler.scrollLoading()
                }
            }
        })
    }

    private fun RecyclerView.scrollLoading() {
        val visibleItemCount: Int =
            (this.layoutManager as LinearLayoutManager).childCount
        val totalItemCount: Int =
            (this.layoutManager as LinearLayoutManager).itemCount
        val pastVisibleItems: Int =
            (this.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        when {
            visibleItemCount + pastVisibleItems >= totalItemCount -> {
                View.VISIBLE.loadingRecycle()
                Handler(Looper.getMainLooper()).postDelayed({
                    getPatientFromApi()
                    View.GONE.loadingRecycle()
                }, LOADING_TIME_OUT)
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(checkGender: MessageEventGender) {
        itemCheckGenderData?.setCheckGenderData(checkGender.message)
        viewModel.checkBoxGender(this.listPatient)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(search: MessageEventSearch) {
        searchingNat = search.message
        viewModel.filter(search.message, listPatient)
    }

    private fun navToBottomSheet() {
        activity?.let { activity ->
            BottomSheetFragment.newInstance().apply {
                listenerSheet = this
            }
                .show(activity.supportFragmentManager, BOTTOM_SHEET)
        }
    }

    private fun Boolean.statusBarNavigation() {
        statusAppBarNavigationBase(
            menu = TRUE_MENU,
            components = ItemComponents(
                appBar = this,
                bottomNavigation = this,
                actionBar = FALSE
            ),
            barColor = R.color.light_blue
        )
    }

    private fun Int.messageToast() {
        toast(getString(this))
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
