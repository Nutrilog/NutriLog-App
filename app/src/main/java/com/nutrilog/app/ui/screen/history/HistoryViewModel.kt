package com.nutrilog.app.ui.screen.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nutrilog.app.core.domain.common.ResultState
import com.nutrilog.app.core.domain.model.Nutrition
import com.nutrilog.app.core.domain.usecase.NutritionUseCase
import com.nutrilog.app.utils.OperationLiveData
import kotlinx.coroutines.launch
import java.util.Date

data class FullDate(
    val date: Int,
    val month: Int,
    val year: Int,
)

class HistoryViewModel(private val useCase: NutritionUseCase) : ViewModel() {
    private val _currentFullDate = MutableLiveData<FullDate>()
    val currentFullDate: LiveData<FullDate> get() = _currentFullDate

    fun setDate(date: Int) {
        _currentFullDate.value =
            FullDate(date, _currentFullDate.value?.month!!, _currentFullDate.value?.year!!)
    }

    fun setMonth(month: Int) {
        _currentFullDate.value =
            FullDate(_currentFullDate.value?.date!!, month, _currentFullDate.value?.year!!)
    }

    fun setFullDate(
        date: Int,
        month: Int,
        year: Int,
    ) {
        _currentFullDate.value = FullDate(date, month, year)
    }

    fun fetchNutrients(date: Date) =
        OperationLiveData<ResultState<List<Nutrition>>> {
            viewModelScope.launch { useCase.fetchNutrients(date).collect { postValue(it) } }
        }
}
