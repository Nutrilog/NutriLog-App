package com.nutrilog.app.data.repository

import com.nutrilog.app.data.remote.request.NutritionFoodRequest
import com.nutrilog.app.data.remote.request.SaveNutritionRequest
import com.nutrilog.app.domain.common.ResultState
import com.nutrilog.app.domain.datasource.NutritionDataSource
import com.nutrilog.app.domain.model.Nutrition
import com.nutrilog.app.domain.model.NutritionFood
import com.nutrilog.app.domain.model.NutritionOption
import com.nutrilog.app.domain.repository.NutritionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import java.util.Date

class NutritionRepositoryImpl(
    private val dataSource: NutritionDataSource,
) : NutritionRepository {
    override suspend fun fetchNutrients(date: Date): Flow<ResultState<List<Nutrition>>> =
        dataSource.fetchNutrients(date).flowOn(
            Dispatchers.IO,
        )

    override suspend fun fetchNutrition(id: String): Flow<ResultState<Nutrition>> =
        dataSource.fetchNutrition(id).flowOn(
            Dispatchers.IO,
        )

    override suspend fun fetchNutritionFood(foodName: String): Flow<ResultState<NutritionFood>> {
        val requestData = NutritionFoodRequest(foodName)
        return dataSource.fetchNutritionFood(requestData).flowOn(
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
        return dataSource.saveNutrition(requestData).flowOn(
            Dispatchers.IO,
        )
    }

    override fun calculateNutritionByDate(date: Date): Flow<Map<NutritionOption, Double>> =
        dataSource.calculateNutritionByDate(date).flowOn(
            Dispatchers.IO,
        )

    override suspend fun clearDataLocalUser(userId: String) = dataSource.clearDataLocalUser(userId)
}
