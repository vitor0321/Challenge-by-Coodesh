package com.example.pharmainc.presentation.ui.fragment.base

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.pharmainc.presentation.model.ItemComponents
import com.example.pharmainc.presentation.ui.activity.PharmaViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

open class BaseFragment(
):Fragment() {
    private val photoDayViewModel: PharmaViewModel by sharedViewModel()

    protected fun statusAppBarNavigationBase(
        appBar: Boolean,
        bottomNavigation: Boolean,
        actionBar: Boolean,
        menu: Boolean,
        barColor: Int
    ) {

        /*show OptionsMenu when inflate*/
        setHasOptionsMenu(menu)

        photoDayViewModel.switchComponent = ItemComponents(appBar, bottomNavigation, actionBar)

        /*change color statusBar*/
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), barColor)
    }
}