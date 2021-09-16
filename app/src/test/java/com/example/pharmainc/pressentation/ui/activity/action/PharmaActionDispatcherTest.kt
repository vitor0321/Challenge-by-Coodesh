package com.example.pharmainc.pressentation.ui.activity.action

import com.example.pharmainc.presentation.ui.activity.PharmaHandler
import com.example.pharmainc.presentation.ui.activity.dispatcher.action.PharmaAction
import com.example.pharmainc.presentation.ui.activity.dispatcher.action.PharmaActionDispatcher
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class PharmaActionDispatcherTest {

    private val handler = mockk<PharmaHandler>(relaxed = true)
    private val dispatcher = PharmaActionDispatcher(handler)

    @Test
    fun `ScreenItems when screenItems action is dispatched`() {

        dispatcher.dispatch(PharmaAction.ScreenItems)

        verify { handler.screenItems() }
    }

    @Test
    fun `ScreenFull when screenFull action is dispatched`() {

        dispatcher.dispatch(PharmaAction.ScreenFull)

        verify { handler.screenFull() }
    }

    @Test
    fun `FilterGender when filterGender action is dispatched`() {

        dispatcher.dispatch(PharmaAction.FilterGender)

        verify { handler.filterGender() }
    }
}