package com.nutrilog.app.core.domain.repository

import com.nutrilog.app.core.data.remote.request.LoginRequest
import com.nutrilog.app.core.data.remote.request.RegisterRequest
import com.nutrilog.app.core.domain.common.ResultState
import com.nutrilog.app.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signUp(request: RegisterRequest): Flow<ResultState<String>>

    suspend fun signIn(request: LoginRequest): Flow<ResultState<String>>

    fun getSession(): Flow<User>

    suspend fun signOut(): Flow<ResultState<String>>
}
