package com.nutrilog.app.core.domain.usecase

import com.nutrilog.app.core.data.remote.request.NutritionFoodRequest
import com.nutrilog.app.core.data.remote.request.SaveNutritionRequest
import com.nutrilog.app.core.domain.common.ResultState
import com.nutrilog.app.core.domain.model.Nutrition
import com.nutrilog.app.core.domain.model.NutritionFood
import com.nutrilog.app.core.domain.model.NutritionOption
import com.nutrilog.app.core.domain.repository.NutritionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import java.util.Date

class NutritionInteractor(private val repository: NutritionRepository): NutritionUseCase {
    override suspend fun fetchNutrients(date: Date): Flow<ResultState<List<Nutrition>>> =
        repository.fetchNutrients(date).flowOn(
            Dispatchers.IO,
        )

    override suspend fun fetchNutrition(id: String): Flow<ResultState<Nutrition>> =
        repository.fetchNutrition(id).flowOn(
            Dispatchers.IO,
        )

    override suspend fun fetchNutritionFood(foodName: String): Flow<ResultState<NutritionFood>> {
        val requestData = NutritionFoodRequest(foodName)
        return repository.fetchNutritionFood(requestData).flowOn(
            Dispatchers.IO,
        )
    }

    override suspend fun saveNutrition(
        foodName: String,
        carbohydrate: Float,
        proteins: Float,
        fat: Float,
        calories: Float,
    ): Flow<ResultState<String>> {
        val requestData = SaveNutritionRequest(foodName, carbohydrate, proteins, fat, calories)
        return repository.saveNutrition(requestData).flowOn(
            Dispatchers.IO,
        )
    }

    override fun calculateNutritionByDate(date: Date): Flow<Map<NutritionOption, Double>> =
        repository.calculateNutritionByDate(date).flowOn(
            Dispatchers.IO,
        )

    override suspend fun clearDataLocalUser(userId: String) = repository.clearDataLocalUser(userId)
}