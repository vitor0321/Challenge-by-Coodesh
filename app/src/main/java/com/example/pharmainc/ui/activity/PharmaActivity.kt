package com.example.pharmainc.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pharmainc.databinding.ActivityPharmaBinding

class PharmaActivity : AppCompatActivity() {

    private var _viewDataBinding: ActivityPharmaBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewDataBinding = ActivityPharmaBinding.inflate(layoutInflater)
        viewDataBinding.lifecycleOwner = this
        setContentView(viewDataBinding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewDataBinding = null
    }
}