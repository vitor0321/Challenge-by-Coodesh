package com.example.pharmainc.presentation.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pharmainc.domain.model.ItemComponents

class PharmaViewModel : ViewModel() {

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