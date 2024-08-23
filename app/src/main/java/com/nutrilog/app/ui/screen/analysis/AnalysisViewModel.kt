package com.nutrilog.app.ui.screen.analysis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nutrilog.app.core.domain.common.ResultState
import com.nutrilog.app.core.domain.model.NutritionFood
import com.nutrilog.app.core.domain.usecase.NutritionUseCase
import com.nutrilog.app.utils.OperationLiveData
import kotlinx.coroutines.launch

class AnalysisViewModel(private val useCase: NutritionUseCase) : ViewModel() {
    fun saveNutrition(
        foodName: String,
        carbohydrate: Float,
        proteins: Float,
        fat: Float,
        calories: Float,
    ) = OperationLiveData<ResultState<String>> {
        viewModelScope.launch {
            useCase.saveNutrition(
                foodName,
                carbohydrate,
                proteins,
                fat,
                calories,
            ).collect { postValue(it) }
        }
    }

    fun fetchNutritionFood(foodName: String) =
        OperationLiveData<ResultState<NutritionFood>> {
            viewModelScope.launch {
                useCase.fetchNutritionFood(foodName).collect { postValue(it) }
            }
        }
}
