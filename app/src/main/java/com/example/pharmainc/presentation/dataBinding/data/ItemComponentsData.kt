package com.example.pharmainc.presentation.dataBinding.data

import androidx.lifecycle.MutableLiveData
import com.example.pharmainc.domain.model.ItemComponents

class ItemComponentsData(
    private var components: ItemComponents = ItemComponents(),
    val appBar: MutableLiveData<Boolean> = MutableLiveData<Boolean>().also {
        it.value = components.appBar
    },
    val bottomNavigation: MutableLiveData<Boolean> = MutableLiveData<Boolean>().also {
        it.value = components.bottomNavigation
    }
) {
    fun setComponentsData(components: ItemComponents) {
        this.components = components
        appBar.postValue(this.components.appBar)
        bottomNavigation.postValue(this.components.bottomNavigation)
    }
}