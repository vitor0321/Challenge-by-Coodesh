package com.example.pharmainc.presentation.ui.fragment.base

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.pharmainc.presentation.ui.activity.PharmaViewModel
import com.example.pharmainc.domain.model.ItemComponents
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


abstract class BaseFragment : Fragment() {

    private val pharmaViewModel: PharmaViewModel by sharedViewModel()
    protected fun statusAppBarNavigationBase(
        menu: Boolean,
        components: ItemComponents,
        barColor: Int
    ) {

        /*show OptionsMenu when inflate*/
        setHasOptionsMenu(menu)

        pharmaViewModel.switchComponent = ItemComponents(
            components.appBar,
            components.bottomNavigation,
            components.actionBar
        )

        /*change color statusBar*/
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), barColor)
    }
}