package com.nutrilog.app.ui.screen.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nutrilog.app.core.domain.common.ResultState
import com.nutrilog.app.core.domain.model.Gender
import com.nutrilog.app.core.domain.model.User
import com.nutrilog.app.utils.OperationLiveData
import kotlinx.coroutines.launch
import java.util.Date

class AuthViewModel(
    private val useCase: com.nutrilog.app.core.domain.usecase.AuthUseCase
) : ViewModel() {
    fun signIn(
        email: String,
        password: String,
    ): LiveData<ResultState<String>> {
        return OperationLiveData<ResultState<String>> {
            viewModelScope.launch {
                useCase.signIn(email, password)
                    .collect {
                        postValue(it)
                    }
            }
        }
    }

    fun signUp(
        name: String,
        email: String,
        password: String,
        gender: Gender,
        dateOfBirth: Date,
    ): LiveData<ResultState<String>> {
        return OperationLiveData<ResultState<String>> {
            viewModelScope.launch {
                useCase.signUp(name, email, password, gender, dateOfBirth)
                    .collect {
                        postValue(it)
                    }
            }
        }
    }

    val user: LiveData<User> =
        OperationLiveData<User> {
            viewModelScope.launch {
                useCase.getSession().collect {
                    postValue(it)
                }
            }
        }

    fun signOut(userId: String): LiveData<ResultState<String>> =
        OperationLiveData<ResultState<String>> {
            viewModelScope.launch {
                useCase.signOut(userId)
                    .collect {
                        postValue(it)
                    }
            }
        }
}
