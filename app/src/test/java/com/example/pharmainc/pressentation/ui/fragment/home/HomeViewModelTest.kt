package com.example.pharmainc.pressentation.ui.fragment.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.pharmainc.domain.mapper.ResultNetworkMapper
import com.example.pharmainc.domain.model.modelnetworl.*
import com.example.pharmainc.domain.usecase.GetPatientUseCase
import com.example.pharmainc.presentation.model.Patient
import com.example.pharmainc.presentation.ui.fragment.home.HomeViewModel
import com.example.pharmainc.presentation.usecase.ClickedCheckBoxUseCase
import com.example.pharmainc.presentation.usecase.SearchingNationalityUseCase
import com.example.pharmainc.presentation.usecase.SearchingNationalityUseCaseImpl
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.mockk.mockk
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

    //Rule controller the Thread
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var homeViewModel: HomeViewModel
    private val patientList = mutableListOf<Patient>()

    private val mapper = ResultNetworkMapper()
    @Mock
    private lateinit var getPatientUseCase: GetPatientUseCase
    @Mock
    private lateinit var searchingNationality: SearchingNationalityUseCase
    @Mock
    private lateinit var clickedCheckBox: ClickedCheckBoxUseCase

    private val dispatcher = TestCoroutineDispatcher()

    private val patientResult = listOf(
        Result(
            gender = "female",
            name = Name(title = "Ms", first = "Heather", last = "Lucas"),
            location = Location(
                street = Street(number = 4150, name = "School Lane"),
                city = "Sheffield",
                state = "Northamptonshire",
                country = "United Kingdom"
            ),
            email = "heather.lucas@example.com",
            dob = Dob(date = "1963-11-26T11:32:31.190Z", age = 58),
            phone = "016977 65304",
            id = Id(name = "NINO", value = "JY 18 48 14 V"),
            picture = Picture(large = "https://randomuser.me/api/portraits/women/92.jpg"),
            nat = "GB"
        )
    )

    @Mock
    private lateinit var apiListLiveDataObserver: Observer<in List<Patient>>

    private fun instantiateViewModel(): HomeViewModel {
        return HomeViewModel(
            getPatientUseCase,
            mapper,
            searchingNationality,
            clickedCheckBox
        )
    }

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        MockitoAnnotations.initMocks(this)
        this.homeViewModel = instantiateViewModel()
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when view model patientResultApi get success then sets apiLiveData`() {
        runBlockingTest {
            //Arrange
            val resultSuccess = MockRepository(patientResult)
            resultSuccess.invoke().apply {
                mapper.fromEntityApiList(this).apply {
                    this.map { patient ->
                        patientList.add(patient)
                    }
                }
            }
            whenever(getPatientUseCase.invoke()).thenReturn(patientResult)
            whenever(clickedCheckBox.onClickedCheckBox(any())).thenReturn(patientList)
            homeViewModel.listPatientLiveData.observeForever(apiListLiveDataObserver)

            // Act
            homeViewModel.getPatients()

            //Assert
            verify(apiListLiveDataObserver).onChanged(patientList)
        }
    }
}

class MockRepository(private val patientRepository: List<Result>) : GetPatientUseCase {

    override suspend fun invoke(): List<Result> {
        return patientRepository
    }
}