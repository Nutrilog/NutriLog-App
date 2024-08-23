package com.nutrilog.app.core.domain.usecase

import com.nutrilog.app.core.data.remote.request.LoginRequest
import com.nutrilog.app.core.data.remote.request.RegisterRequest
import com.nutrilog.app.core.domain.common.ResultState
import com.nutrilog.app.core.domain.model.Gender
import com.nutrilog.app.core.domain.model.User
import com.nutrilog.app.core.domain.repository.AuthRepository
import com.nutrilog.app.core.domain.repository.NutritionRepository
import com.nutrilog.app.utils.helpers.convertDateToString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import java.util.Date

class AuthInteractor(
    private val repository: AuthRepository,
    private val repositoryNutrition: NutritionRepository
): AuthUseCase {
    override suspend fun signUp(
        name: String,
        email: String,
        password: String,
        gender: Gender,
        dateOfBirth: Date
    ): Flow<ResultState<String>> {
        val convertDateToString = dateOfBirth.convertDateToString()
        val requestData = RegisterRequest(name, email, password, gender.label, convertDateToString)
        return repository.signUp(requestData).flowOn(Dispatchers.IO)
    }

    override suspend fun signIn(
        email: String,
        password: String,
    ): Flow<ResultState<String>> {
        val requestData = LoginRequest(email, password)
        return repository.signIn(requestData).flowOn(Dispatchers.IO)
    }

    override fun getSession(): Flow<User> = repository.getSession().flowOn(Dispatchers.IO)

    override suspend fun signOut(userId: String): Flow<ResultState<String>> {
        repositoryNutrition.clearDataLocalUser(userId)
        return repository.signOut().flowOn(Dispatchers.IO)
    }
}