package com.nutrilog.app.domain.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val token: String,
)
