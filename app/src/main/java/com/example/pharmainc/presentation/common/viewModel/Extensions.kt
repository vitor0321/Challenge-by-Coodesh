package com.example.pharmainc.presentation.common.viewModel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

fun <T> LiveData<T>.observe(lifecycleOwner: LifecycleOwner, dispatcher: Dispatcher<T>) {
    observe(lifecycleOwner) {
        dispatcher.dispatch(it)
    }
}