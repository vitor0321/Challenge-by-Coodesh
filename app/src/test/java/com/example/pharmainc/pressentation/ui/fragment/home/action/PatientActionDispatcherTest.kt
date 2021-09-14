package com.example.pharmainc.pressentation.ui.fragment.home.action

import com.example.pharmainc.presentation.model.Patient
import com.example.pharmainc.presentation.ui.fragment.home.dispatcher.HomeHandler
import com.example.pharmainc.presentation.ui.fragment.home.dispatcher.action.HomeAction
import com.example.pharmainc.presentation.ui.fragment.home.dispatcher.action.HomeActionDispatcher
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class PatientActionDispatcherTest {

    private val handler = mockk<HomeHandler>(relaxed = true)
    private val dispatcher = HomeActionDispatcher(handler)

    @Test
    fun `Go to Detail when go to detail action is dispatched`() {
        val patient = mockk<Patient>()

        dispatcher.dispatch(HomeAction.GoToDetail(patient))

        verify { handler.goToDetail(patient) }
    }
}