package com.example.pharmainc.presentation.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.pharmainc.R
import com.example.pharmainc.databinding.ActivityPharmaBinding
import com.example.pharmainc.presentation.constants.GENDER_DIALOG
import com.example.pharmainc.presentation.dataBinding.data.ItemComponentsData
import com.example.pharmainc.presentation.ui.dialog.GenderDialog
import com.example.photoday.ui.toast.Toast.toast
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

                //background menu navigation
                bottomNavMainActivity.background = null
                /*Action Bar Gone*/
                supportActionBar?.hide()
                controller.addOnDestinationChangedListener { _, _, _ ->
                    /* change the fragment title as it is in the nav_graph Label */
                    title = null
                }

                /* control all bottom navigation navigation */
                bottomNavMainActivity.setupWithNavController(controller)
            } catch (e: Exception) {
                messageToast(R.string.failure_initialize_control)
            }

        }
    }

    private fun filterGenderPatient() {
        viewDataBinding.apply {
            clickFilterGender = View.OnClickListener {
                GenderDialog.newInstance()
                    .show(supportFragmentManager, GENDER_DIALOG)
            }
        }
    }

    private fun filterNationality() {
        viewDataBinding.apply {
        }
    }

    private fun messageToast(message: Int) {
        toast(this.getString(message))
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewDataBinding = null
    }
}