package com.nutrilog.app.presentation.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.viewbinding.ViewBinding
import com.nutrilog.app.domain.model.Language
import com.nutrilog.app.presentation.ui.main.profile.ProfileViewModel
import com.nutrilog.app.utils.helpers.observe
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale

abstract class BaseActivity<viewBinding : ViewBinding> : AppCompatActivity() {
    protected lateinit var binding: viewBinding
        private set

    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            bindingInflater.invoke(layoutInflater).apply {
                setContentView(root)
            }

        setupEdgeToEdge()
        onViewBindingCreated(savedInstanceState)
        observe(profileViewModel.getLanguage(), ::setLanguage)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    protected open fun onViewBindingCreated(savedInstanceState: Bundle?) {}

    private fun setupEdgeToEdge() {
        enableEdgeToEdge()
        binding.apply {
            ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    leftMargin = systemBars.left
                    bottomMargin = systemBars.bottom
                    rightMargin = systemBars.right
                    topMargin = systemBars.top
                }

                WindowInsetsCompat.CONSUMED
            }
        }
    }

    private fun setLanguage(language: Language) {
        val valueLanguage = language.value
        Locale.setDefault(Locale(valueLanguage))
        val localeList = LocaleListCompat.forLanguageTags(valueLanguage)
        AppCompatDelegate.setApplicationLocales(localeList)
    }

    abstract val bindingInflater: (LayoutInflater) -> viewBinding
}
