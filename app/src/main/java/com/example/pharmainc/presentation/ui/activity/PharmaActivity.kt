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
import com.example.pharmainc.presentation.eventBus.MessageEventSearch
import com.example.pharmainc.presentation.constants.EMPTY
import com.example.pharmainc.presentation.constants.GENDER_DIALOG
import com.example.pharmainc.presentation.dataBinding.data.ItemComponentsData
import com.example.pharmainc.presentation.ui.dialog.GenderDialog
import com.example.photoday.ui.toast.Toast.toast
import org.greenrobot.eventbus.EventBus
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE

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
    private var searchingNat: String = EMPTY

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
        viewModel.component.observe(this, { components ->
            when (components.actionBar) {
                TRUE -> supportActionBar?.show()
                FALSE -> supportActionBar?.hide()
            }
            itemComponentsData.setComponentsData(components)
        })
    }

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
                R.string.failure_initialize_control.messageToast()
            }

        }
    }

    private fun filterGenderPatient() {
        viewDataBinding.apply {
            clickFilterGender = View.OnClickListener {
                when (searchingNat) {
                    EMPTY -> {
                        GenderDialog.newInstance()
                            .show(supportFragmentManager, GENDER_DIALOG)
                    }
                }
            }
        }
    }

    private fun filterNationality() {
        viewDataBinding.apply {
            val searching = editTextSearching
            searching.afterTextChanged { afterTextChanged ->
                searchingNat = afterTextChanged
                EventBus.getDefault().post(MessageEventSearch(afterTextChanged))
            }
        }
    }

    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }

    private fun Int.messageToast() {
        toast(getString(this))
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewDataBinding = null
    }
}