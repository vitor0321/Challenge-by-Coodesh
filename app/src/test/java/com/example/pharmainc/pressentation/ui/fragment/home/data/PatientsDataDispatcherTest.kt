package com.example.pharmainc.pressentation.ui.fragment.home.data

import com.example.pharmainc.data.db.entity.PatientEntity
import com.example.pharmainc.presentation.ui.PatientHandler
import com.example.pharmainc.presentation.ui.fragment.home.data.PatientsDataDispatcher
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class PatientsDataDispatcherTest {

    private val handler = mockk<PatientHandler>(relaxed = true)
    private val dispatcher = PatientsDataDispatcher(handler)

    @Test
    fun `Bind Data is dispatched`() {
        val patients = mockk<List<PatientEntity>>()
        dispatcher.dispatch(patients)

        verify { handler.bindData(patients) }
    }
}