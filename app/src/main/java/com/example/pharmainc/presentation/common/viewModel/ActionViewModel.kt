package com.example.pharmainc.presentation.common.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class ActionViewModel<Action, Data> : ViewModel() {

    private val actionsMutableLiveData = SingleLiveEvent<Action>()
    private val dataMutableLiveData = MutableLiveData<List<Data>>()

    val actions: LiveData<Action> = actionsMutableLiveData
    val data: LiveData<List<Data>> = dataMutableLiveData

    fun dispatchAction(action: Action) {
        action?.let { action ->
            actionsMutableLiveData.value = action
        }
    }

    fun dispatchData(data: List<Data>) {
        dataMutableLiveData.value = data
    }
}