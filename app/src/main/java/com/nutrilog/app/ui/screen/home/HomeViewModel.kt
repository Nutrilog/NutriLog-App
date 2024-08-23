package com.nutrilog.app.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nutrilog.app.core.domain.model.NutritionOption
import com.nutrilog.app.core.domain.usecase.NutritionUseCase
import com.nutrilog.app.utils.OperationLiveData
import kotlinx.coroutines.launch
import java.util.Date

class HomeViewModel(private val useCase: NutritionUseCase) : ViewModel() {
    fun calculateNutrients(date: Date) =
        OperationLiveData<Map<NutritionOption, Double>> {
            viewModelScope.launch {
                useCase.calculateNutritionByDate(date).collect { postValue(it) }
            }
        }
}
