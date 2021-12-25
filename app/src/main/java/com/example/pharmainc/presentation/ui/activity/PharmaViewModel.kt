package com.example.pharmainc.presentation.ui.activity

import com.example.pharmainc.presentation.common.viewModel.ActionViewModel
import com.example.pharmainc.presentation.constants.EMPTY
import com.example.pharmainc.presentation.dataBinding.data.ItemComponentsData
import com.example.pharmainc.presentation.model.ItemComponents
import com.example.core.domain.model.Patient
import com.example.pharmainc.presentation.ui.activity.dispatcher.action.PharmaAction

class PharmaViewModel(
    private val itemComponentsData: ItemComponentsData,
) : ActionViewModel<PharmaAction, Patient>() {

    private var searchingNat: String = EMPTY
    var switchComponent: ItemComponents = ItemComponents()
        set(value) {
            field = value
            initComponents(value)
        }

    private fun initComponents(itemComponents: ItemComponents) {
        itemComponentsData.setComponentsData(itemComponents)
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