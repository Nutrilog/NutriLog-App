package com.nutrilog.app.core.domain.usecase

import com.nutrilog.app.core.domain.common.ResultState
import com.nutrilog.app.core.domain.model.Gender
import com.nutrilog.app.core.domain.model.User
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface AuthUseCase {
    suspend fun signUp(
        name: String,
        email: String,
        password: String,
        gender: Gender,
        dateOfBirth: Date,
    ): Flow<ResultState<String>>

    suspend fun signIn(
        email: String,
        password: String,
    ): Flow<ResultState<String>>

    fun getSession(): Flow<User>

    suspend fun signOut(userId: String): Flow<ResultState<String>>
}