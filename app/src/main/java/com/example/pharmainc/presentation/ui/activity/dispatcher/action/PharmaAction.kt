package com.example.pharmainc.presentation.ui.activity.dispatcher.action

sealed class PharmaAction {
    object ScreenItems : PharmaAction()
    object ScreenFull : PharmaAction()
    object FilterGender : PharmaAction()
}
