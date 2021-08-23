package com.example.pharmainc.presentation.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmainc.R
import com.example.pharmainc.databinding.FragmentHomeBinding
import com.example.pharmainc.presentation.constants.*
import com.example.pharmainc.presentation.dataBinding.data.ItemCheckGenderData
import com.example.pharmainc.presentation.dataBinding.data.PatientData
import com.example.pharmainc.presentation.eventBus.MessageEventGender
import com.example.pharmainc.presentation.eventBus.MessageEventSearch
import com.example.pharmainc.presentation.model.Patient
import com.example.pharmainc.presentation.ui.fragment.base.BaseFragment
import com.example.pharmainc.presentation.ui.fragment.bottomSheet.BottomSheetFragment
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

    private var itemCheckGenderData: ItemCheckGenderData? = null
    private val adapterHome: HomeAdapter by inject()
    private val viewModel: HomeViewModel by viewModel()
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
        statusBarNavigation(TRUE)
        viewModel.viewFlipperControl(CHILD_FIRST)
        initObserver()
        initEventBus()
        initRecycleView()
        getPatientFromApi()
    }

    private fun initObserver() {
        viewModel.apiErrorLiveData.observe(viewLifecycleOwner) { setErrorView() }
        viewModel.loadingRecycle.observe(viewLifecycleOwner) { loadingRecycle(it) }
        viewModel.childLiveData.observe(viewLifecycleOwner) { child(it) }
        viewModel.listPatientLiveData.observe(viewLifecycleOwner) { upDateAdapterListPatient(it) }
    }

    private fun setErrorView() {
        viewDataBinding.errorMessage.visibility = View.VISIBLE
        viewDataBinding.viewAnimationLoading.visibility = View.GONE
        messageToast(R.string.error_api_401)
        statusBarNavigation(FALSE)
    }

    private fun initEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    private fun getPatientFromApi() = viewModel.getPatients()

    private fun initRecycleView() {
        viewDataBinding.apply {
            recyclerviewItemHome.run {
                layoutManager = LinearLayoutManager(context)
                this.adapter = adapterHome
                adapterHome.onClickListener = { itemPatient ->
                    clickCardRecycleView(itemPatient)
                }
                onScrollListener()
            }
        }
    }

    private fun clickCardRecycleView(itemPatient: Patient) {
        setDataPatient(itemPatient)
        navToBottomSheet()
    }

    private fun setDataPatient(patient: Patient) =
        itemPatientData.setItemPatientData(patient)

    private fun upDateAdapterListPatient(listPatient: List<Patient>) {
        adapterHome.submitList(listPatient)
        viewModel.viewFlipperControl(CHILD_SECOND)
    }

    private fun child(child: Int) {
        viewDataBinding.viewFlipperHome.displayedChild = child
        viewDataBinding.errorMessage.visibility= View.INVISIBLE
        statusBarNavigation(TRUE)
    }

    private fun onScrollListener() {
        viewDataBinding.recyclerviewItemHome.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recycler: RecyclerView, dx: Int, dy: Int) {
                scrollLoading(recycler)
            }
        })
    }

    private fun scrollLoading(recycler: RecyclerView) {
        val visibleItemCount: Int =
            (recycler.layoutManager as LinearLayoutManager).childCount
        val totalItemCount: Int =
            (recycler.layoutManager as LinearLayoutManager).itemCount
        val pastVisibleItems: Int =
            (recycler.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

        viewModel.scrollLoading(visibleItemCount, totalItemCount, pastVisibleItems)
    }

    private fun loadingRecycle(status: Int) {
        viewDataBinding.loadingViewRecycler.visibility = status
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(checkGender: MessageEventGender) {
        itemCheckGenderData?.setCheckGenderData(checkGender.message)
        viewModel.filterSearching(EMPTY)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(search: MessageEventSearch) {
        viewModel.filterSearching(search.message)
    }

    private fun navToBottomSheet() {
        activity?.let { activity ->
            BottomSheetFragment.newInstance().apply {
                listenerSheet = this
            }
                .show(activity.supportFragmentManager, BOTTOM_SHEET)
        }
    }

    private fun statusBarNavigation(statusBar: Boolean) {
        statusAppBarNavigationBase(
            appBar = statusBar,
            bottomNavigation = statusBar,
            actionBar = FALSE,
            menu = TRUE_MENU,
            barColor = R.color.light_blue
        )
    }

    private fun messageToast(message: Int) {
        toast(getString(message))
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
