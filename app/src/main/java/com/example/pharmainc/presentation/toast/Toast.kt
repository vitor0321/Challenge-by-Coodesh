package com.example.pharmainc.presentation.toast

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.pharmainc.R
import com.example.pharmainc.presentation.constants.EMPTY
import com.example.pharmainc.databinding.ItemToastConstumBinding

object Toast {
    @SuppressLint("StaticFieldLeak")
    private var dataBinding: ItemToastConstumBinding? = null
    private val viewDataBinding get() = dataBinding!!

    fun Fragment.toast(message: String, time: Int = Toast.LENGTH_SHORT) {
        when {
            message != EMPTY -> Toast(context).apply {
                val layoutToast =
                    LayoutInflater.from(context).inflate(R.layout.item_toast_constum, null)
                dataBinding = ItemToastConstumBinding.bind(layoutToast)
                viewDataBinding.itemToast = ItemToast(message)
                duration = time
                view = layoutToast
            }.show()
        }
        dataBinding = null
    }

    fun Activity.toast(message: String, time: Int = Toast.LENGTH_SHORT) {
        when {
            message != EMPTY -> Toast(this).apply {
                val layoutToast = LayoutInflater.from(this@toast)
                    .inflate(R.layout.item_toast_constum, null)
                dataBinding = ItemToastConstumBinding.bind(layoutToast)
                viewDataBinding.itemToast = ItemToast(message)
                duration = time
                view = layoutToast
            }.show()
        }
        dataBinding = null
    }
}