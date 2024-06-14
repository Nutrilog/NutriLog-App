package com.nutrilog.app.presentation.ui.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nutrilog.app.data.pref.SettingPreference
import com.nutrilog.app.domain.model.ActiveLevel
import com.nutrilog.app.domain.model.Language
import kotlinx.coroutines.launch

class ProfileViewModel(private val preference: SettingPreference) : ViewModel() {
    /**
     * first: ActiveLevel - Active level of user
     * second: Double - Weight of user
     * third: Double - Height of user
     * @return LiveData<ActiveLevel, Double, Double>
     */
    private val userData = MediatorLiveData<Triple<ActiveLevel, Double, Double>>()

    fun getLanguage(): LiveData<Language> = preference.getLanguageSetting().asLiveData()

    fun getUserData(): LiveData<Triple<ActiveLevel, Double, Double>> {
        val activeLevelLiveData = preference.getActiveLevelSetting().asLiveData()
        val weightLiveData = preference.getWeightUser().asLiveData()
        val heightLiveData = preference.getHeightUser().asLiveData()

        userData.addSource(activeLevelLiveData) { activeLevel ->
            val weight = weightLiveData.value ?: return@addSource
            val height = heightLiveData.value ?: return@addSource
            userData.value = Triple(activeLevel, weight, height)
        }

        userData.addSource(weightLiveData) { weight ->
            val activeLevel = activeLevelLiveData.value ?: return@addSource
            val height = heightLiveData.value ?: return@addSource
            userData.value = Triple(activeLevel, weight, height)
        }

        userData.addSource(heightLiveData) { height ->
            val activeLevel = activeLevelLiveData.value ?: return@addSource
            val weight = weightLiveData.value ?: return@addSource
            userData.value = Triple(activeLevel, weight, height)
        }

        return userData
    }

    fun saveLanguageSetting(language: Language) {
        viewModelScope.launch {
            preference.saveLanguageSetting(language)
        }
    }

    fun saveActiveLevelSetting(activeLevel: ActiveLevel) {
        viewModelScope.launch {
            preference.setActiveLevelSetting(activeLevel)
        }
    }

    fun saveUserWeight(weight: Double) {
        viewModelScope.launch {
            preference.setWeightUser(weight)
        }
    }

    fun saveUserHeight(height: Double) {
        viewModelScope.launch {
            preference.setHeightUser(height)
        }
    }
}
