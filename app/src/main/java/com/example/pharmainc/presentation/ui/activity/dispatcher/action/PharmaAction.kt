package com.example.pharmainc.presentation.ui.activity.dispatcher.action

sealed class PharmaAction {
    object ShowActionBar : PharmaAction()
    object HideActionBar : PharmaAction()
    object FilterGender : PharmaAction()
}
