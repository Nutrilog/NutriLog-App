package com.nutrilog.app.core.data.remote.service

import com.nutrilog.app.core.data.remote.request.LoginRequest
import com.nutrilog.app.core.data.remote.request.RegisterRequest
import com.nutrilog.app.core.data.remote.response.BasicResponse
import com.nutrilog.app.core.data.remote.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("register")
    suspend fun register(
        @Body request: RegisterRequest,
    ): BasicResponse

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest,
    ): LoginResponse
}
