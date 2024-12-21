package com.nutrilog.app.core.data.datasource

import com.nutrilog.app.core.data.local.room.NutrilogDatabase
import com.nutrilog.app.core.data.remote.request.NutritionFoodRequest
import com.nutrilog.app.core.data.remote.request.SaveNutritionRequest
import com.nutrilog.app.core.data.remote.service.NutritionService
import com.nutrilog.app.core.domain.common.ResultState
import com.nutrilog.app.core.domain.common.StatusResponse
import com.nutrilog.app.core.domain.model.Nutrition
import com.nutrilog.app.core.domain.model.NutritionFood
import com.nutrilog.app.core.domain.model.NutritionOption
import com.nutrilog.app.utils.helpers.convertDateToString
import com.nutrilog.app.utils.helpers.convertListToNutritionLevel
import com.nutrilog.app.core.utils.createResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.util.Date

class NutritionDataSource(
    private val database: NutrilogDatabase,
    private val service: NutritionService,
) {
    suspend fun fetchNutrients(date: Date): Flow<ResultState<List<Nutrition>>> =
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

    suspend fun fetchNutrition(id: String): Flow<ResultState<Nutrition>> =
        flow {
            emit(ResultState.Loading)
            try {
                val response = database.getNutritionDao().getNutrition(id)

                emit(ResultState.Success(response))
            } catch (e: Exception) {
                emit(ResultState.Error(e.createResponse()?.message ?: ""))
            }
        }

    suspend fun fetchNutritionFood(request: NutritionFoodRequest): Flow<ResultState<NutritionFood>> =
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

    suspend fun saveNutrition(request: SaveNutritionRequest): Flow<ResultState<String>> =
        flow {
            emit(ResultState.Loading)
            try {
                val response = service.analyze(request)
                if (response.status === StatusResponse.ERROR) {
                    emit(ResultState.Error(response.message))
                    return@flow
                }
                Timber.tag("Respon Data").e(response.data.toString())
                database.getNutritionDao().insertNutrition(response.data)
                emit(ResultState.Success(response.message))
            } catch (e: Exception) {
                emit(ResultState.Error(e.createResponse()?.message ?: ""))
            }
        }

    fun calculateNutritionByDate(date: Date): Flow<Map<NutritionOption, Double>> =
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

    suspend fun clearDataLocalUser(userId: String) {
        database.getNutritionDao().deleteByUserId(userId)
    }
}
