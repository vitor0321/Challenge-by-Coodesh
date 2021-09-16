package com.example.pharmainc.pressentation.ui.fragment.home.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.pharmainc.domain.mapper.dao.PatientEntityMapperUseCase
import com.example.pharmainc.domain.mapper.network.ResultMapperUseCase
import com.example.pharmainc.domain.repository.usecase.PatientRepositoryUseCase
import com.example.pharmainc.presentation.common.test.TestObserver
import com.example.pharmainc.presentation.model.Patient
import com.example.pharmainc.presentation.ui.fragment.home.HomeViewModel
import com.example.pharmainc.presentation.ui.fragment.home.dispatcher.action.HomeAction
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
    private lateinit var clickedCheckBoxUseCase: ClickedCheckBoxUseCase

    @Mock
    private lateinit var patientRepositoryUseCase: PatientRepositoryUseCase

    @Mock
    private lateinit var searchingNationalityUseCase: SearchingNationalityUseCase

    @ExperimentalCoroutinesApi
    private val dispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockitoAnnotations.initMocks(this)
        this.viewModel =
            HomeViewModel(
                clickedCheckBoxUseCase,
                patientRepositoryUseCase,
                searchingNationalityUseCase
            )
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
            val patient = mockk<Patient>()
            val testObserver = TestObserver<HomeAction>()

            viewModel.actions.observeForever(testObserver)

            viewModel.goToDetail(patient)

            assertEquals(HomeAction.GoToDetail(patient), testObserver.lastValue())
        }
    }
}