package com.nutrilog.app.core.di.module

import com.nutrilog.app.core.data.datasource.AuthDataSource
import com.nutrilog.app.core.data.repository.AuthRepositoryImpl
import com.nutrilog.app.core.domain.repository.AuthRepository
import org.koin.dsl.module

val authRepositoryModule =
    module {
        single { AuthDataSource(get(), get()) }
        single<AuthRepository> { AuthRepositoryImpl(get()) }
    }

