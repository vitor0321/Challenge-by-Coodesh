package com.example.pharmainc.presentation.ui.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.pharmainc.R
import com.example.pharmainc.databinding.ActivityPharmaBinding
import com.example.pharmainc.presentation.constants.GENDER_DIALOG
import com.example.pharmainc.presentation.dataBinding.data.ItemComponentsData
import com.example.pharmainc.presentation.eventBus.MessageEventSearch
import com.example.pharmainc.presentation.ui.dialog.GenderDialog
import com.example.photoday.ui.toast.Toast.toast
import org.greenrobot.eventbus.EventBus
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PharmaActivity : AppCompatActivity() {

    private var _viewDataBinding: ActivityPharmaBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    private val viewModel: PharmaViewModel by viewModel {
        parametersOf()
    }
    private val itemComponentsData: ItemComponentsData by inject {
        parametersOf(this)
    }
    private lateinit var controller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewDataBinding = ActivityPharmaBinding.inflate(layoutInflater)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.components = itemComponentsData
        setContentView(viewDataBinding.root)
        init()
    }

    private fun init() {
        initializeControl()
        initObserve()
        filterGenderPatient()
        filterNationality()
    }

    private fun initObserve() {
        viewModel.showActionBarLiveData.observe(this, { showActionBar() })
        viewModel.hideActionBarLiveData.observe(this, { hideActionBar() })
        viewModel.filterGenderLiveData.observe(this, { initGenderDialog() })
    }

    private fun showActionBar() = supportActionBar?.show()

    private fun hideActionBar() = supportActionBar?.hide()

    private fun initializeControl() {
        viewDataBinding.apply {
            try {
                val navHostFragment = supportFragmentManager
                    .findFragmentById(R.id.main_activity_nav_host) as NavHostFragment
                controller = navHostFragment.navController
                controller.graph.setStartDestination(R.id.splashFragment)
                bottomNavMainActivity.background = null
                supportActionBar?.hide()
                controller.addOnDestinationChangedListener { _, _, _ ->
                    title = null
                }
                bottomNavMainActivity.setupWithNavController(controller)
            } catch (e: Exception) {
                messageToast(R.string.failure_initialize_control)
            }
        }
    }

    private fun filterGenderPatient() {
        viewDataBinding.apply {
            clickFilterGender = View.OnClickListener { viewModel.clickFilterGender() }
        }
    }

    private fun initGenderDialog() {
        GenderDialog.newInstance()
            .show(supportFragmentManager, GENDER_DIALOG)
    }

    private fun filterNationality() {
        viewDataBinding.apply {
            val textSearching = editTextSearching
            afterTextChanged(textSearching) { afterTextChanged ->
                viewModel.searchingNat(afterTextChanged)
                EventBus.getDefault().post(MessageEventSearch(afterTextChanged))
            }
        }
    }

    private fun afterTextChanged(editText: EditText, afterTextChanged: (String) -> Unit) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }

    private fun messageToast(message: Int) {
        toast(getString(message))
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewDataBinding = null
    }
}