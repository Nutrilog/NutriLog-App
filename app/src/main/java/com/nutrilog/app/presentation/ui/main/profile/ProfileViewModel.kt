package com.nutrilog.app.presentation.ui.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nutrilog.app.data.pref.SettingPreference
import com.nutrilog.app.domain.model.Language
import kotlinx.coroutines.launch

class ProfileViewModel(private val preference: SettingPreference): ViewModel() {
    fun getLanguage():LiveData<Language> = preference.getLanguageSetting().asLiveData()

    fun saveLanguageSetting(language: Language) {
        viewModelScope.launch {
            preference.saveLanguageSetting(language)
        }
    }
}