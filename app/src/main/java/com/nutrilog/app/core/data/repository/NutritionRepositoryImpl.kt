package com.nutrilog.app.core.data.repository

import com.nutrilog.app.core.data.datasource.NutritionDataSource
import com.nutrilog.app.core.data.remote.request.NutritionFoodRequest
import com.nutrilog.app.core.data.remote.request.SaveNutritionRequest
import com.nutrilog.app.core.domain.common.ResultState
import com.nutrilog.app.core.domain.model.Nutrition
import com.nutrilog.app.core.domain.model.NutritionFood
import com.nutrilog.app.core.domain.model.NutritionOption
import com.nutrilog.app.core.domain.repository.NutritionRepository
import kotlinx.coroutines.flow.Flow
import java.util.Date

class NutritionRepositoryImpl(
    private val dataSource: NutritionDataSource,
) : NutritionRepository {
    override suspend fun fetchNutrients(date: Date): Flow<ResultState<List<Nutrition>>> = dataSource.fetchNutrients(date)

    override suspend fun fetchNutrition(id: String): Flow<ResultState<Nutrition>> = dataSource.fetchNutrition(id)

    override suspend fun fetchNutritionFood(request: NutritionFoodRequest): Flow<ResultState<NutritionFood>> = dataSource.fetchNutritionFood(request)

    override suspend fun saveNutrition(request: SaveNutritionRequest): Flow<ResultState<String>> = dataSource.saveNutrition(request)

    override fun calculateNutritionByDate(date: Date): Flow<Map<NutritionOption, Double>> = dataSource.calculateNutritionByDate(date)

    override suspend fun clearDataLocalUser(userId: String) = dataSource.clearDataLocalUser(userId)
}
