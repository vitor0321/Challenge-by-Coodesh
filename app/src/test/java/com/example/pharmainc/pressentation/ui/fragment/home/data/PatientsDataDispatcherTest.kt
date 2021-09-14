package com.example.pharmainc.pressentation.ui.fragment.home.data

import com.example.pharmainc.presentation.model.Patient
import com.example.pharmainc.presentation.ui.fragment.home.dispatcher.HomeHandler
import com.example.pharmainc.presentation.ui.fragment.home.dispatcher.data.HomeDataDispatcher
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class PatientsDataDispatcherTest {

    private val handler = mockk<HomeHandler>(relaxed = true)
    private val dispatcher = HomeDataDispatcher(handler)

    @Test
    fun `Bind Data is dispatched`() {
        val patients = mockk<List<Patient>>()
        dispatcher.dispatch(patients)

        verify { handler.bindData(patients) }
    }
}