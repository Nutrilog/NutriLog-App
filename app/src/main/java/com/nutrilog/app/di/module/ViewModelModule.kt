package com.nutrilog.app.di.module

import com.nutrilog.app.ui.screen.analysis.AnalysisViewModel
import com.nutrilog.app.ui.screen.auth.AuthViewModel
import com.nutrilog.app.ui.screen.history.HistoryViewModel
import com.nutrilog.app.ui.screen.home.HomeViewModel
import com.nutrilog.app.ui.screen.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule =
    module {
        viewModel { AuthViewModel(get()) }
        viewModel { ProfileViewModel(get()) }
        viewModel { HomeViewModel(get()) }
        viewModel { HistoryViewModel(get()) }
        viewModel { AnalysisViewModel(get()) }
    }
