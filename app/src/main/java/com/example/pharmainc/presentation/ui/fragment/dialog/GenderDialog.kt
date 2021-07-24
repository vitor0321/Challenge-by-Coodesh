package com.example.pharmainc.presentation.ui.fragment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.pharmainc.databinding.DialogGenderBinding
import com.example.pharmainc.presentation.dataBinding.data.ItemCheckGenderData
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class GenderDialog : DialogFragment() {
    private var _viewDataBinding: DialogGenderBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    var listener: GenderListener? = null
    private val itemCheckGender: ItemCheckGenderData by inject {
        parametersOf(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _viewDataBinding = DialogGenderBinding.inflate(inflater, container, false)
        viewDataBinding.checkGender = itemCheckGender
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        choiceSelect()
    }

    private fun choiceSelect() {
        viewDataBinding.apply {
            cancelButton = View.OnClickListener { dismiss() }
            okButton = View.OnClickListener {
                listener?.onAccessSelected()
                dismiss()
            }
        }

    }

    interface GenderListener {
        fun onAccessSelected()
    }

    override fun onDestroy() {
        this._viewDataBinding = null
        listener
        super.onDestroy()
    }

    companion object {
        fun newInstance() = GenderDialog()
    }
}