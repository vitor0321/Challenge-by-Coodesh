package com.example.pharmainc.pressentation.ui.fragment.home.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.pharmainc.presentation.common.test.TestObserver
import com.example.pharmainc.domain.mapper.ResultMapperUseCase
import com.example.pharmainc.domain.usecase.network.GetResultUseCase
import com.example.pharmainc.data.db.entity.PatientEntity
import com.example.pharmainc.presentation.ui.fragment.home.action.PatientAction
import com.example.pharmainc.presentation.ui.fragment.home.HomeViewModel
import com.example.pharmainc.presentation.usecase.ClickedCheckBoxUseCase
import com.example.pharmainc.presentation.usecase.SearchingNationalityUseCase
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel

    @Mock
    private lateinit var mapper: ResultMapperUseCase

    @Mock
    private lateinit var getPatientUseCase: GetResultUseCase

    @Mock
    private lateinit var searchingNationality: SearchingNationalityUseCase

    @Mock
    private lateinit var clickedCheckBox: ClickedCheckBoxUseCase

    @ExperimentalCoroutinesApi
    private val dispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockitoAnnotations.initMocks(this)
        this.viewModel =
            HomeViewModel(getPatientUseCase, mapper, searchingNationality, clickedCheckBox)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `On product detail clicked, go to product detail is emitted`() {
        runBlockingTest {
            val patient = mockk<PatientEntity>()
            val testObserver = TestObserver<PatientAction>()

            viewModel.actions.observeForever(testObserver)

            viewModel.goToDetail(patient)

            assertEquals(PatientAction.GoToDetail(patient), testObserver.lastValue())
        }
    }
}