package com.nutrilog.app.core.domain.common

sealed class ResultState<out R> private constructor() {
    data object Loading : ResultState<Nothing>()

    data class Success<out T>(val data: T) : ResultState<T>()

    data class Error(val message: String) : ResultState<Nothing>()
}
