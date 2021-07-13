package com.example.pharmainc.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pharmainc.constants.ERROR_400
import com.example.pharmainc.constants.ERROR_401
import com.example.pharmainc.constants.ERROR_500
import com.example.pharmainc.dataApi.PatientResult
import com.example.pharmainc.dataApi.repository.PatientRepository
import com.example.pharmainc.ui.model.ItemComponents

class PharmaViewModel() : ViewModel() {

    val component: LiveData<ItemComponents> get() = _component
    private var _component: MutableLiveData<ItemComponents> =
        MutableLiveData<ItemComponents>().also {
            it.value = switchComponent
        }
    var switchComponent: ItemComponents = ItemComponents()
        set(value) {
            field = value
            _component.value = value
        }

}