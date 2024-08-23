package com.nutrilog.app.core.di.module

import android.app.Application
import androidx.room.Room
import com.nutrilog.app.core.data.local.room.NutrilogDatabase
import com.nutrilog.app.utils.constant.AppConstant.DB_NAME
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

fun provideDatabase(application: Application): NutrilogDatabase {
    return Room.databaseBuilder(application, NutrilogDatabase::class.java, DB_NAME)
        .fallbackToDestructiveMigration()
        .build()
}

fun provideNutritionDao(database: NutrilogDatabase) = database.getNutritionDao()

val localModule =
    module {
        single { provideNutritionDao(get()) }
        single { provideDatabase(androidApplication()) }
    }
