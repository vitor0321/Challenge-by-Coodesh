package com.example.pharmainc.pressentation.ui.fragment.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.pharmainc.domain.model.Patient
import com.example.pharmainc.network.PatientResult
import com.example.pharmainc.network.mapper.ResultNetworkMapper
import com.example.pharmainc.network.model.*
import com.example.pharmainc.network.repository.PatientRepository
import com.example.pharmainc.presentation.constants.ERROR_400
import com.example.pharmainc.presentation.constants.ERROR_401
import com.example.pharmainc.presentation.constants.ERROR_500
import com.example.pharmainc.presentation.dataBinding.data.ItemCheckGenderData
import com.example.pharmainc.presentation.ui.fragment.home.HomeViewModel
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class HomeViewModelTest {

    //Rule controller the Thread
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var patientList: List<Patient>

    @Mock
    private lateinit var apiLiveDataObserver: Observer<in Pair<Int?, List<Patient>?>>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `when view model patientResultApi get success then sets apiLiveData`() {
        //Arrange
        val patient = listOf(
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
        val resultSuccess = MockRepository(PatientResult.Success(patient))
        homeViewModel = HomeViewModel(resultSuccess, ResultNetworkMapper(), ItemCheckGenderData())
        val mapper = ResultNetworkMapper()
        mapper.fromEntityApiList(patient).apply {
            patientList = this
        }
        homeViewModel.apiLiveData.observeForever(apiLiveDataObserver)

        // Act
        homeViewModel.getPatient()

        //Assert
        verify(apiLiveDataObserver).onChanged(Pair(first = null, second = patientList))
    }

    @Test
    fun `when view model patientResultApi get ApiError 401 then sets apiLiveData`() {
        //Arrange
        val resultApiError = MockRepository(PatientResult.ApiError(401))
        homeViewModel =
            HomeViewModel(resultApiError, ResultNetworkMapper(), ItemCheckGenderData())
        homeViewModel.apiLiveData.observeForever(apiLiveDataObserver)

        //Act
        homeViewModel.getPatient()

        //Assert
        verify(apiLiveDataObserver).onChanged(Pair(first = ERROR_401, second = null))
    }

    @Test
    fun `when view model patientResultApi get ApiError then sets apiLiveData`() {
        //Arrange
        val resultApiError = MockRepository(PatientResult.ApiError(400))
        homeViewModel =
            HomeViewModel(resultApiError, ResultNetworkMapper(), ItemCheckGenderData())
        homeViewModel.apiLiveData.observeForever(apiLiveDataObserver)

        //Act
        homeViewModel.getPatient()

        //Assert
        verify(apiLiveDataObserver).onChanged(Pair(first = ERROR_400, second = null))
    }

    @Test
    fun `when view model patientResultApi get ServerError then sets apiLiveData`() {
        //Arrange
        val resultServerError = MockRepository(PatientResult.ServerError)
        homeViewModel =
            HomeViewModel(resultServerError, ResultNetworkMapper(), ItemCheckGenderData())
        homeViewModel.apiLiveData.observeForever(apiLiveDataObserver)

        //Act
        homeViewModel.getPatient()

        //Assert
        verify(apiLiveDataObserver).onChanged(Pair(first = ERROR_500, second = null))
    }
}

class MockRepository(private val result: PatientResult) : PatientRepository {
    override fun getPatient(patientResultCallback: (result: PatientResult) -> Unit) {
        patientResultCallback(result)
    }
}
