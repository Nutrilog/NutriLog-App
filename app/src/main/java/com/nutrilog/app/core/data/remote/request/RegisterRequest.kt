package com.nutrilog.app.core.data.remote.request

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val gender: String,
    @field:SerializedName("date_of_birth")
    val dateOfBirth: String,
)
