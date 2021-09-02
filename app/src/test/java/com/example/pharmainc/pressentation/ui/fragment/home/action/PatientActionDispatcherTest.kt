package com.example.pharmainc.pressentation.ui.fragment.home.action

import com.example.pharmainc.presentation.model.Patient
import com.example.pharmainc.presentation.ui.fragment.home.PatientHandler
import com.example.pharmainc.presentation.ui.fragment.home.action.PatientAction
import com.example.pharmainc.presentation.ui.fragment.home.action.PatientActionDispatcher
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class PatientActionDispatcherTest {

    private val handler = mockk<PatientHandler>(relaxed = true)
    private val dispatcher = PatientActionDispatcher(handler)

    @Test
    fun `Go to Detail when go to detail action is dispatched`() {
        val patient = mockk<Patient>()

        dispatcher.dispatch(PatientAction.GoToDetail(patient))

        verify { handler.goToDetail(patient) }
    }
}