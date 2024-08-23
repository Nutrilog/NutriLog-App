package com.nutrilog.app.core.di.module

import com.nutrilog.app.core.data.pref.SettingPreference
import com.nutrilog.app.core.data.pref.UserSessionPreference
import org.koin.dsl.module

val preferenceModule =
    module {
        single { UserSessionPreference(get()) }
        single { SettingPreference(get()) }
    }
