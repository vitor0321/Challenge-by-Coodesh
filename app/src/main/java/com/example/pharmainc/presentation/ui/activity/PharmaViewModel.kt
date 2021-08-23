package com.example.pharmainc.presentation.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pharmainc.presentation.constants.EMPTY
import com.example.pharmainc.presentation.constants.TRUE
import com.example.pharmainc.presentation.dataBinding.data.ItemComponentsData
import com.example.pharmainc.presentation.model.ItemComponents
import java.lang.Boolean.FALSE

class PharmaViewModel(
    private val itemComponentsData: ItemComponentsData,
) : ViewModel() {

    private val _showActionBarLiveData = MutableLiveData<Boolean>()
    val showActionBarLiveData: LiveData<Boolean> get() = _showActionBarLiveData

    private val _hideActionBarLiveData = MutableLiveData<Boolean>()
    val hideActionBarLiveData: LiveData<Boolean> get() = _hideActionBarLiveData

    private val _filterGenderLiveData = MutableLiveData<Boolean>()
    val filterGenderLiveData: LiveData<Boolean> get() = _filterGenderLiveData

    private var searchingNat: String = EMPTY
    var switchComponent: ItemComponents = ItemComponents()
        set(value) {
            field = value
            initComponents(value)
            supportActionBar(value)
        }

    private fun initComponents(itemComponents: ItemComponents) {
        itemComponentsData.setComponentsData(itemComponents)
    }

    private fun supportActionBar(itemComponents: ItemComponents) {
        when (itemComponents.actionBar) {
            TRUE -> _showActionBarLiveData.value = TRUE
            FALSE -> _hideActionBarLiveData.value = FALSE
        }
    }

    fun clickFilterGender() {
        when (searchingNat) {
            EMPTY -> _filterGenderLiveData.value = FALSE
        }
    }

    fun searchingNat(afterTextChanged: String) {
        searchingNat = afterTextChanged
    }
}