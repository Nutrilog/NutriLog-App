package com.nutrilog.app.domain.repository

import com.nutrilog.app.domain.common.ResultState
import com.nutrilog.app.domain.model.Nutrition
import com.nutrilog.app.domain.model.NutritionFood
import com.nutrilog.app.domain.model.NutritionOption
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface NutritionRepository {
    suspend fun fetchNutrients(date: Date): Flow<ResultState<List<Nutrition>>>

    suspend fun fetchNutrition(id: String): Flow<ResultState<Nutrition>>

    suspend fun fetchNutritionFood(foodName: String): Flow<ResultState<NutritionFood>>

    suspend fun saveNutrition(
        foodName: String,
        carbohydrate: Float,
        proteins: Float,
        fat: Float,
        calories: Float,
    ): Flow<ResultState<String>>

    fun calculateNutritionByDate(date: Date): Flow<Map<NutritionOption, Double>>

    suspend fun clearDataLocalUser(userId: String)
}
