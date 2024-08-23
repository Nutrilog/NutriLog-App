package com.nutrilog.app.core.domain.repository

import com.nutrilog.app.core.data.remote.request.NutritionFoodRequest
import com.nutrilog.app.core.data.remote.request.SaveNutritionRequest
import com.nutrilog.app.core.domain.common.ResultState
import com.nutrilog.app.core.domain.model.Nutrition
import com.nutrilog.app.core.domain.model.NutritionFood
import com.nutrilog.app.core.domain.model.NutritionOption
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface NutritionRepository {
    suspend fun fetchNutrients(date: Date): Flow<ResultState<List<Nutrition>>>

    suspend fun fetchNutrition(id: String): Flow<ResultState<Nutrition>>

    suspend fun fetchNutritionFood(request: NutritionFoodRequest): Flow<ResultState<NutritionFood>>

    suspend fun saveNutrition(request: SaveNutritionRequest): Flow<ResultState<String>>

    fun calculateNutritionByDate(date: Date): Flow<Map<NutritionOption, Double>>

    suspend fun clearDataLocalUser(userId: String)
}
