package com.nutrilog.app.di.module

import com.nutrilog.app.presentation.ui.analysis.AnalysisViewModel
import com.nutrilog.app.presentation.ui.auth.AuthViewModel
import com.nutrilog.app.presentation.ui.main.history.HistoryViewModel
import com.nutrilog.app.presentation.ui.main.home.HomeViewModel
import com.nutrilog.app.presentation.ui.main.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule =
    module {
        viewModel { AuthViewModel(get(), get()) }
        viewModel { ProfileViewModel(get()) }
        viewModel { HomeViewModel(get()) }
        viewModel { HistoryViewModel(get()) }
        viewModel { AnalysisViewModel(get()) }
    }
