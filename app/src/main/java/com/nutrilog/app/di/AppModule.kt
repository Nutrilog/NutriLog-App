package com.nutrilog.app.di

import com.nutrilog.app.di.module.authUseCaseModule
import com.nutrilog.app.di.module.nutritionUseCaseModule
import com.nutrilog.app.di.module.viewModelModule

val appModule =
    listOf(
        viewModelModule,
        authUseCaseModule,
        nutritionUseCaseModule
    )
