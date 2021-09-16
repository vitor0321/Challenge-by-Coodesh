package com.example.pharmainc.presentation.common.test

import androidx.lifecycle.Observer

class TestObserver<T> : Observer<T> {

    private val values = mutableListOf<T>()

    override fun onChanged(value: T) {
        values.add(value)
    }

    fun lastValue() = values.last()
}