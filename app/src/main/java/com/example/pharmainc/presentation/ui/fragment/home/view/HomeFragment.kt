package com.example.pharmainc.presentation.ui.fragment.home.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmainc.R
import com.example.pharmainc.common.viewModel.observe
import com.example.pharmainc.databinding.FragmentHomeBinding
import com.example.pharmainc.presentation.constants.*
import com.example.pharmainc.presentation.dataBinding.data.ItemCheckGenderData
import com.example.pharmainc.presentation.dataBinding.data.PatientData
import com.example.pharmainc.presentation.eventBus.MessageEventGender
import com.example.pharmainc.presentation.eventBus.MessageEventSearch
import com.example.pharmainc.presentation.model.Patient
import com.example.pharmainc.presentation.ui.fragment.base.BaseFragment
import com.example.pharmainc.presentation.ui.fragment.detail.DetailFragment
import com.example.pharmainc.presentation.ui.fragment.home.PatientHandler
import com.example.pharmainc.presentation.ui.fragment.home.action.PatientActionDispatcher
import com.example.pharmainc.presentation.ui.fragment.home.data.PatientsDataDispatcher
import com.example.pharmainc.presentation.ui.fragment.home.viewModel.HomeViewModel
import com.example.photoday.ui.toast.Toast.toast
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE

class HomeFragment : BaseFragment(), PatientHandler {

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

        viewModel.actions.observe(viewLifecycleOwner, PatientActionDispatcher(this))
        viewModel.data.observe(viewLifecycleOwner, PatientsDataDispatcher(this))

        return viewDataBinding.root
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init() {
        setView(GONE, CHILD_FIRST, GONE, TRUE)
        initEventBus()
        initRecycleView()
    }

    override fun goToDetail(data: Patient) {
        itemPatientData.setItemPatientData(data)
        activity?.let { activity ->
            DetailFragment.newInstance().apply {
                listenerSheet = this
            }
                .show(activity.supportFragmentManager, BOTTOM_SHEET)
        }
    }

    override fun bindData(data: List<Patient>) {
        adapterHome.submitList(data)
        Handler(Looper.getMainLooper()).postDelayed({
            setView(GONE, CHILD_SECOND, GONE, TRUE)
        }, LOADING_TIME_OUT)
    }

    override fun showLoading() {
        setView(VISIBLE, CHILD_SECOND, GONE, TRUE)
    }

    override fun showError() {
        setView(GONE, CHILD_SECOND, VISIBLE, FALSE)
        messageToast(R.string.error_api_401)
    }

    private fun setView(loading: Int, child: Int,error: Int, statusBar: Boolean) {
        viewDataBinding.apply {
            loadingViewRecycler.visibility = loading
            viewFlipperHome.displayedChild = child
            errorMessage.visibility = error
        }
        statusBarNavigation(statusBar)
    }

    private fun initEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    private fun initRecycleView() {
        viewDataBinding.recyclerviewItemHome.run {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapterHome
            adapterHome.onClickListener = { itemPatient ->
                viewModel.goToDetail(itemPatient)
            }
            onScrollListener()
        }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(checkGender: MessageEventGender) {
        itemCheckGenderData?.setCheckGenderData(checkGender.message)
        viewModel.filterSearching(EMPTY)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(search: MessageEventSearch) {
        viewModel.filterSearching(search.message)
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
