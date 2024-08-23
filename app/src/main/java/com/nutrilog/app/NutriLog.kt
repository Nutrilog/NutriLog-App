package com.nutrilog.app

import android.app.Application
import com.nutrilog.app.core.di.coreModule
import com.nutrilog.app.di.appModule
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class NutriLog : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        val appModules = listOf(
            *coreModule.toTypedArray(),
            *appModule.toTypedArray(),
        )

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@NutriLog)
            modules(appModules)
        }
    }
}
