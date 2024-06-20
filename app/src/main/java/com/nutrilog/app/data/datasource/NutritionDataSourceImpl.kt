package com.nutrilog.app.data.datasource

import com.nutrilog.app.data.local.room.NutrilogDatabase
import com.nutrilog.app.data.remote.request.NutritionFoodRequest
import com.nutrilog.app.data.remote.request.SaveNutritionRequest
import com.nutrilog.app.data.remote.service.NutritionService
import com.nutrilog.app.domain.common.ResultState
import com.nutrilog.app.domain.common.StatusResponse
import com.nutrilog.app.domain.datasource.NutritionDataSource
import com.nutrilog.app.domain.model.Nutrition
import com.nutrilog.app.domain.model.NutritionFood
import com.nutrilog.app.domain.model.NutritionOption
import com.nutrilog.app.utils.helpers.convertDateToString
import com.nutrilog.app.utils.helpers.convertListToNutritionLevel
import com.nutrilog.app.utils.helpers.createResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date

class NutritionDataSourceImpl(
    private val database: NutrilogDatabase,
    private val service: NutritionService,
) : NutritionDataSource {
    override suspend fun fetchNutrients(date: Date): Flow<ResultState<List<Nutrition>>> =
        flow {
            val localData = database.getNutritionDao().getNutritionByDate(date)
            if (localData.isNotEmpty()) {
                emit(ResultState.Success(localData))
            } else {
                emit(ResultState.Loading)
            }

            try {
                val response = service.getNutrients(date.convertDateToString())
                if (response.status === StatusResponse.ERROR) {
                    emit(ResultState.Error(response.message))
                    return@flow
                }

                val data = response.data

                database.getNutritionDao().insertAllNutrition(data)
                emit(ResultState.Success(data))
            } catch (e: Exception) {
                emit(ResultState.Error(e.createResponse()?.message ?: ""))
            }
        }

    override suspend fun fetchNutrition(id: String): Flow<ResultState<Nutrition>> =
        flow {
            emit(ResultState.Loading)
            try {
                val response = database.getNutritionDao().getNutrition(id)

                emit(ResultState.Success(response))
            } catch (e: Exception) {
                emit(ResultState.Error(e.createResponse()?.message ?: ""))
            }
        }

    override suspend fun fetchNutritionFood(request: NutritionFoodRequest): Flow<ResultState<NutritionFood>> =
        flow {
            emit(ResultState.Loading)

            try {
                val response = service.getNutritionFood(request)
                if (response.status === StatusResponse.ERROR) {
                    emit(ResultState.Error(response.message))
                    return@flow
                }

                emit(ResultState.Success(response.data))
            } catch (e: Exception) {
                emit(ResultState.Error(e.createResponse()?.message ?: ""))
            }
        }

    override suspend fun saveNutrition(request: SaveNutritionRequest): Flow<ResultState<String>> =
        flow {
            emit(ResultState.Loading)
            try {
                val response = service.analyze(request)
                if (response.status === StatusResponse.ERROR) {
                    emit(ResultState.Error(response.message))
                    return@flow
                }

                database.getNutritionDao().insertNutrition(response.data)
                emit(ResultState.Success(response.message))
            } catch (e: Exception) {
                emit(ResultState.Error(e.createResponse()?.message ?: ""))
            }
        }

    override fun calculateNutritionByDate(date: Date): Flow<Map<NutritionOption, Double>> =
        flow {
            val list = database.getNutritionDao().getNutritionByDate(date)

            emit(convertListToNutritionLevel(list))

            try {
                val response = service.getNutrients(date.convertDateToString())

                if (response.status === StatusResponse.SUCCESS) {
                    database.getNutritionDao().insertAllNutrition(response.data)
                    emit(convertListToNutritionLevel(response.data))
                }
            } catch (e: Exception) {
                emit(convertListToNutritionLevel(list))
            }
        }

    override suspend fun clearDataLocalUser(userId: String) {
        database.getNutritionDao().deleteByUserId(userId)
    }
}
