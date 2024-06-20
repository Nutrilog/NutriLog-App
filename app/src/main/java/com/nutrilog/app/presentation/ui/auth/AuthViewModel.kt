package com.nutrilog.app.presentation.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nutrilog.app.domain.common.ResultState
import com.nutrilog.app.domain.model.Gender
import com.nutrilog.app.domain.model.User
import com.nutrilog.app.domain.repository.AuthRepository
import com.nutrilog.app.domain.repository.NutritionRepository
import com.nutrilog.app.presentation.common.OperationLiveData
import kotlinx.coroutines.launch
import java.util.Date

class AuthViewModel(
    private val repository: AuthRepository,
    private val repositoryNutrition: NutritionRepository,
) : ViewModel() {
    fun signIn(
        email: String,
        password: String,
    ): LiveData<ResultState<String>> {
        return OperationLiveData<ResultState<String>> {
            viewModelScope.launch {
                repository.signIn(email, password)
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
                repository.signUp(name, email, password, gender, dateOfBirth)
                    .collect {
                        postValue(it)
                    }
            }
        }
    }

    val user: LiveData<User> =
        OperationLiveData<User> {
            viewModelScope.launch {
                repository.getSession().collect {
                    postValue(it)
                }
            }
        }

    fun signOut(userId: String): LiveData<ResultState<String>> =
        OperationLiveData<ResultState<String>> {
            viewModelScope.launch {
                repository.signOut()
                    .collect {
                        postValue(it)
                    }
                repositoryNutrition.clearDataLocalUser(userId)
            }
        }
}
