package com.nutrilog.app.di.module

import com.nutrilog.app.core.domain.usecase.AuthInteractor
import com.nutrilog.app.core.domain.usecase.AuthUseCase
import com.nutrilog.app.core.domain.usecase.NutritionInteractor
import com.nutrilog.app.core.domain.usecase.NutritionUseCase
import org.koin.dsl.module

val authUseCaseModule = module {
    factory<AuthUseCase> {
        AuthInteractor(
            get(),
            get()
        )
    }
}

val nutritionUseCaseModule =
    module {
        factory<NutritionUseCase> { NutritionInteractor(get()) }
    }

