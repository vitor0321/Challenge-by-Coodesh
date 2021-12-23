package com.example.pharmainc.presentation.ui.fragment.home

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
import com.example.pharmainc.databinding.FragmentHomeBinding
import com.example.pharmainc.presentation.common.viewModel.observe
import com.example.pharmainc.presentation.constants.CHILD_FIRST
import com.example.pharmainc.presentation.constants.BOTTOM_SHEET
import com.example.pharmainc.presentation.constants.EMPTY
import com.example.pharmainc.presentation.constants.TRUE_MENU
import com.example.pharmainc.presentation.constants.LOADING_TIME_OUT
import com.example.pharmainc.presentation.constants.CHILD_SECOND
import com.example.pharmainc.presentation.dataBinding.data.ItemCheckGenderData
import com.example.pharmainc.presentation.dataBinding.data.PatientData
import com.example.pharmainc.presentation.eventBus.MessageEventGender
import com.example.pharmainc.presentation.eventBus.MessageEventSearch
import com.example.pharmainc.presentation.model.Patient
import com.example.pharmainc.presentation.toast.ToastMessage.toast
import com.example.pharmainc.presentation.ui.fragment.base.BaseFragment
import com.example.pharmainc.presentation.ui.fragment.detail.DetailFragment
import com.example.pharmainc.presentation.ui.fragment.home.dispatcher.HomeHandler
import com.example.pharmainc.presentation.ui.fragment.home.dispatcher.action.HomeActionDispatcher
import com.example.pharmainc.presentation.ui.fragment.home.dispatcher.data.HomeDataDispatcher
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE

@Suppress("TooManyFunctions")
class HomeFragment : BaseFragment(), HomeHandler {

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

        viewModel.actions.observe(viewLifecycleOwner, HomeActionDispatcher(this))
        viewModel.data.observe(viewLifecycleOwner, HomeDataDispatcher(this))
        return viewDataBinding.root
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init() {
        viewModel.getPatientDao()
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
        setView(GONE, CHILD_FIRST, VISIBLE, FALSE)
        toast(getString(R.string.error_api_401))
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
            menu = TRUE_MENU,
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
