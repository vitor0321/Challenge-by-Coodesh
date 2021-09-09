package com.example.pharmainc.presentation.common.viewModel

interface Dispatcher<T> {

    fun dispatch(item: T)
}