package com.nutrilog.app.ui.screen.analysis

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nutrilog.app.core.domain.common.ResultState
import com.nutrilog.app.core.domain.usecase.NutritionUseCase
import com.nutrilog.app.utils.DummyData
import com.nutrilog.app.utils.MainDispatcherRule
import com.nutrilog.app.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AnalysisViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var useCase: NutritionUseCase

    private lateinit var viewModel: AnalysisViewModel

    private val dummyIdFood = DummyData.idFoodDummy
    private val dummyFoodName = DummyData.foodNameDummy
    private val dummyCarbohydrate = DummyData.carbohydrateDummy
    private val dummyProteins = DummyData.proteinsDummy
    private val dummyFat = DummyData.fatDummy
    private val dummyCalories = DummyData.caloriesDummy
    private val dummyNutrition = DummyData.nutritionDummy

    @Before
    fun setUp() {
        useCase = mock(NutritionUseCase::class.java)
        viewModel = AnalysisViewModel(useCase)
    }

    @Test
    fun `saveNutrition success should return Success result`() = runTest {
        val expectedMessage = "Nutrition saved successfully"
        val expectedResult = ResultState.Success(expectedMessage)

        `when`(useCase.saveNutrition(dummyFoodName, dummyCarbohydrate, dummyProteins, dummyFat, dummyCalories))
            .thenReturn(flowOf(expectedResult))

        val result = viewModel.saveNutrition(dummyFoodName, dummyCarbohydrate, dummyProteins, dummyFat, dummyCalories).getOrAwaitValue()

        assertTrue(result is ResultState.Success)
        assertEquals(expectedMessage, (result as ResultState.Success).data)
    }

    @Test
    fun `saveNutrition failure should return Error result`() = runTest {
        val errorMessage = "Failed to save nutrition"
        val expectedResult = ResultState.Error(errorMessage)

        `when`(useCase.saveNutrition(dummyFoodName, dummyCarbohydrate, dummyProteins, dummyFat, dummyCalories))
            .thenReturn(flowOf(expectedResult))

        val result = viewModel.saveNutrition(dummyFoodName, dummyCarbohydrate, dummyProteins, dummyFat, dummyCalories).getOrAwaitValue()

        assertTrue(result is ResultState.Error)
        assertEquals(errorMessage, (result as ResultState.Error).message)
    }

    @Test
    fun `fetchNutritionFood success should return Success result`() = runTest {
        val expectedNutrition = dummyNutrition
        val expectedResult = ResultState.Success(expectedNutrition)

        `when`(useCase.fetchNutritionFood(dummyFoodName))
            .thenReturn(flowOf(expectedResult))

        val result = viewModel.fetchNutritionFood(dummyFoodName).getOrAwaitValue()

        assertTrue(result is ResultState.Success)
        assertEquals(expectedNutrition, (result as ResultState.Success).data)
    }

    @Test
    fun `fetchNutritionFood failure should return Error result`() = runTest {
        val foodName = "NonexistentFood"
        val errorMessage = "Food not found"
        val expectedResult = ResultState.Error(errorMessage)

        `when`(useCase.fetchNutritionFood(foodName))
            .thenReturn(flowOf(expectedResult))

        val result = viewModel.fetchNutritionFood(foodName).getOrAwaitValue()

        assertTrue(result is ResultState.Error)
        assertEquals(errorMessage, (result as ResultState.Error).message)
    }
}