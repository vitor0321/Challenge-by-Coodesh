package com.example.pharmainc.pressentation.ui.fragment.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.pharmainc.domain.mapper.ResultNetworkMapper
import com.example.pharmainc.domain.model.modelnetworl.*
import com.example.pharmainc.domain.usecase.GetPatientUseCase
import com.example.pharmainc.presentation.extensions.CheckListPatient
import com.example.pharmainc.presentation.model.Patient
import com.example.pharmainc.presentation.ui.fragment.home.HomeViewModel
import com.nhaarman.mockitokotlin2.verify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
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
    private val getPatientUseCase = mockk<GetPatientUseCase>()
    private val resultNetworkMapper = mockk<ResultNetworkMapper>()
    private val checkListPatient = mockk<CheckListPatient>()
    private val patientResult = listOf(
        Result(
            gender = "gender 1",
            name = Name(
                title = "title 1",
                first = "first 1",
                last = "last 1"
            ),
            location = Location(
                street = Street(
                    number = 1,
                    name = "name 1",
                ),
                city = "city 1",
                state = "state 1",
                country = "country 1",
            ),
            email = "email 1",
            dob = Dob(
                date = "1977-11-10T10:46:00.210Z",
                age = 1
            ),
            phone = "phone 1",
            id = Id(
                name = "name 1",
                value = "value 1"
            ),
            picture = Picture(
                large = "large 1"
            ),
            nat = "nat 1"
        )
    )

    @Mock
    private lateinit var apiListLiveDataObserver: Observer<in List<Patient>>

    private fun instantiateViewModel(): HomeViewModel {
        return HomeViewModel(
            getPatientUseCase,
            resultNetworkMapper,
            checkListPatient
        )
    }

    @Before
    fun setup() {
        this.homeViewModel = instantiateViewModel()
        MockitoAnnotations.initMocks(this)
    }

    @ExperimentalCoroutinesApi
    @Test
    suspend fun `when view model patientResultApi get success then sets apiLiveData`() =
        runBlockingTest {
            //Arrange
            val resultSuccess = MockRepository(patientResult)
            resultSuccess.invoke().apply {
                resultNetworkMapper.fromEntityApiList(this).apply {
                    this.map { patient ->
                        patientList.add(patient)
                    }
                }
            }
            homeViewModel.apiListLiveData.observeForever(apiListLiveDataObserver)

            // Act
            homeViewModel.getPatients()

            //Assert
            verify(apiListLiveDataObserver).onChanged(patientList)
        }
}

class MockRepository(private val patientRepository: List<Result>) : GetPatientUseCase {

    override suspend fun invoke(): List<Result> {
        return patientRepository
    }
}