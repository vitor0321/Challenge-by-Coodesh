package com.example.pharmainc.presentation.common.viewModel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observe(lifecycleOwner: LifecycleOwner, dispatcher: Dispatcher<T>) {
    observe(lifecycleOwner, Observer {
        dispatcher.dispatch(it)
    })
}