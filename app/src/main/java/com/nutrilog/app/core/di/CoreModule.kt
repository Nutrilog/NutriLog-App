package com.nutrilog.app.core.di

import com.nutrilog.app.core.di.module.authRepositoryModule
import com.nutrilog.app.core.di.module.localModule
import com.nutrilog.app.core.di.module.nutritionRepositoryModule
import com.nutrilog.app.core.di.module.preferenceModule
import com.nutrilog.app.core.di.module.remoteModule

val coreModule = listOf(
    authRepositoryModule,
    localModule,
    nutritionRepositoryModule,
    preferenceModule,
    remoteModule
)