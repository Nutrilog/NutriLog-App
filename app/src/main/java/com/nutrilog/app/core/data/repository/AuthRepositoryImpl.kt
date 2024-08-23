package com.nutrilog.app.core.data.repository

import com.nutrilog.app.core.data.datasource.AuthDataSource
import com.nutrilog.app.core.data.remote.request.LoginRequest
import com.nutrilog.app.core.data.remote.request.RegisterRequest
import com.nutrilog.app.core.domain.common.ResultState
import com.nutrilog.app.core.domain.model.User
import com.nutrilog.app.core.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(
    private val dataSource: AuthDataSource,
) : AuthRepository {
    override suspend fun signUp(request: RegisterRequest): Flow<ResultState<String>> = dataSource.signUp(request)

    override suspend fun signIn(request: LoginRequest): Flow<ResultState<String>> = dataSource.signIn(request)

    override fun getSession(): Flow<User> = dataSource.getSession()

    override suspend fun signOut(): Flow<ResultState<String>> = dataSource.signOut()
}
