package com.example.pharmainc.common.viewModel

interface Dispatcher<T> {

    fun dispatch(item: T)
}