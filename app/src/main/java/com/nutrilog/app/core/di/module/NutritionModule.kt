package com.nutrilog.app.core.di.module

import com.nutrilog.app.core.data.datasource.NutritionDataSource
import com.nutrilog.app.core.data.repository.NutritionRepositoryImpl
import com.nutrilog.app.core.domain.repository.NutritionRepository
import org.koin.dsl.module

val nutritionRepositoryModule =
    module {
        single { NutritionDataSource(get(), get()) }
        single<NutritionRepository> { NutritionRepositoryImpl(get()) }
    }