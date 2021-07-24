package com.example.pharmainc.presentation.ui.fragment.bottomSheet

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pharmainc.R
import com.example.pharmainc.databinding.FragmentBottomSheetBinding
import com.example.pharmainc.presentation.dataBinding.data.PatientData
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class BottomSheetFragment : BottomSheetDialogFragment() {

    private var _viewDataBinding: FragmentBottomSheetBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    var listenerSheet: BottomSheetFragment? = null
    private val itemPatientData: PatientData by inject {
        parametersOf(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewDataBinding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        this.viewDataBinding.itemPatient = itemPatientData
        viewDataBinding.root.setBackgroundColor(resources.getColor(R.color.transparent))
        return viewDataBinding.root
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init() {
        closeBottomSheet()
        setBackgroundBottomSheet()
    }

    private fun closeBottomSheet() {
        viewDataBinding.clickCloseBottomSheet = View.OnClickListener { dismiss() }
    }

    private fun setBackgroundBottomSheet() {
        context?.let { context ->
            BottomSheetDialog(context).apply {
                setOnShowListener {
                    (view?.parent as ViewGroup).background =
                        ColorDrawable(Color.TRANSPARENT)
                }
                show()
                dismiss()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this._viewDataBinding = null
        this.listenerSheet = null
    }

    companion object {
        fun newInstance() = BottomSheetFragment()
    }
}