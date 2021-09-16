package com.example.pharmainc.presentation.ui.activity

import com.example.pharmainc.presentation.common.viewModel.ActionViewModel
import com.example.pharmainc.presentation.constants.EMPTY
import com.example.pharmainc.presentation.constants.TRUE
import com.example.pharmainc.presentation.dataBinding.data.ItemComponentsData
import com.example.pharmainc.presentation.model.ItemComponents
import com.example.pharmainc.presentation.model.Patient
import com.example.pharmainc.presentation.ui.activity.dispatcher.action.PharmaAction
import java.lang.Boolean.FALSE

class PharmaViewModel(
    private val itemComponentsData: ItemComponentsData,
) : ActionViewModel<PharmaAction, Patient>() {

    private var searchingNat: String = EMPTY
    var switchComponent: ItemComponents = ItemComponents()
        set(value) {
            field = value
            initComponents(value)
            stateScreen(value)
        }

    private fun initComponents(itemComponents: ItemComponents) {
        itemComponentsData.setComponentsData(itemComponents)
    }

    private fun stateScreen(itemComponents: ItemComponents) {
        when (itemComponents.screen) {
            TRUE -> dispatchAction(PharmaAction.ScreenItems)
            FALSE -> dispatchAction(PharmaAction.ScreenFull)
        }
    }

    fun clickFilterGender() {
        when (searchingNat) {
            EMPTY -> dispatchAction(PharmaAction.FilterGender)
        }
    }

    fun searchingNat(afterTextChanged: String) {
        searchingNat = afterTextChanged
    }
}