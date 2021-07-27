package com.example.pharmainc.presentation.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.pharmainc.databinding.DialogGenderBinding
import com.example.pharmainc.domain.eventBus.MessageEventGender
import com.example.pharmainc.domain.model.ItemCheckGender
import com.example.pharmainc.presentation.dataBinding.data.ItemCheckGenderData
import org.greenrobot.eventbus.EventBus
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class GenderDialog : DialogFragment() {
    private var _viewDataBinding: DialogGenderBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    private val itemCheckGenderData: ItemCheckGenderData by inject {
        parametersOf(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _viewDataBinding = DialogGenderBinding.inflate(inflater, container, false)
        viewDataBinding.checkGender = itemCheckGenderData
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

                val message = MessageEventGender(ItemCheckGender(
                    female = checkboxFemale.isChecked,
                    male = checkboxFemale.isChecked
                ))
                EventBus.getDefault().post(message)
                dismiss()
            }
        }

    }

    override fun onDestroy() {
        this._viewDataBinding = null
        super.onDestroy()
    }

    companion object {
        fun newInstance() = GenderDialog()
    }
}