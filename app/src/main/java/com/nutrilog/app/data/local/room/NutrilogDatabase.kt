package com.nutrilog.app.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nutrilog.app.data.local.dao.NutritionDao
import com.nutrilog.app.domain.model.Nutrition
import com.nutrilog.app.utils.RoomConverters

@Database(
    entities = [Nutrition::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(RoomConverters::class)
abstract class NutrilogDatabase : RoomDatabase() {
    abstract fun getNutritionDao(): NutritionDao
}
