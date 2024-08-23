package com.nutrilog.app.core.data.remote.response

import com.google.gson.annotations.SerializedName
import com.nutrilog.app.core.domain.common.StatusResponse
import com.nutrilog.app.core.domain.model.User

data class LoginResponse(
    @field:SerializedName("data")
    val data: User,
    @field:SerializedName("status")
    val status: StatusResponse,
    @field:SerializedName("message")
    val message: String,
)
