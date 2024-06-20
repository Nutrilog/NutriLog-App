package com.nutrilog.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nutrilog.app.domain.model.Nutrition
import java.util.Date

@Dao
interface NutritionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllNutrition(nutrients: List<Nutrition>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNutrition(nutrient: Nutrition)

    @Query("SELECT * FROM nutrition WHERE created_at = :date ORDER BY created_at DESC")
    fun getNutritionByDate(date: Date): List<Nutrition>

    @Query("SELECT * FROM nutrition WHERE id = :id")
    fun getNutrition(id: String): Nutrition

    @Query("DELETE FROM nutrition WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM nutrition WHERE created_at = :date")
    suspend fun deleteByDate(date: Date)

    @Query("DELETE FROM nutrition WHERE user_id = :userId")
    suspend fun deleteByUserId(userId: String)
}
