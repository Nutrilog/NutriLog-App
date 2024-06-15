package com.nutrilog.app.presentation.ui.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nutrilog.app.R
import com.nutrilog.app.databinding.FragmentProfileBinding
import com.nutrilog.app.domain.common.ResultState
import com.nutrilog.app.domain.model.ActiveLevel
import com.nutrilog.app.domain.model.Language
import com.nutrilog.app.presentation.ui.auth.AuthViewModel
import com.nutrilog.app.presentation.ui.base.BaseFragment
import com.nutrilog.app.presentation.ui.base.component.dialog.EditProfileDialogFragment
import com.nutrilog.app.utils.helpers.gone
import com.nutrilog.app.utils.helpers.observe
import com.nutrilog.app.utils.helpers.show
import com.nutrilog.app.utils.helpers.showSnackBar
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    private val authViewModel: AuthViewModel by viewModel()
    private val profileViewModel: ProfileViewModel by viewModel()
    private var defaultValues: EditProfileDialogFragment.DefaultValues =
        EditProfileDialogFragment.DefaultValues(0.0, 0.0)

    private var currentLanguage: Language = Language.ENGLISH

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileBinding =
        FragmentProfileBinding::inflate

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel.getSession().observe(viewLifecycleOwner) {
            binding.tvUsername.text = it.name
            binding.tvEmail.text = it.email
        }

        initObserver()
        initUI()
    }

    private fun initObserver() {
        observe(profileViewModel.getLanguage(), ::handleLanguageChange)
        observe(profileViewModel.getUserData(), ::handleUserData)
    }

    private fun initUI() {
        binding.apply {
            tvTitleProfile.text = getString(R.string.label_nav_profile)

            switchTranslate.setOnCheckedChangeListener { _, isChecked ->
                applyLanguage(!isChecked)
            }

            logoutSection.setOnClickListener {
                observe(authViewModel.signOut, ::onSignOutResult)
            }

            selectActionLevel.setOnSpinnerItemSelectedListener<String> { oldIndex, _, newIndex, _ ->
                if (oldIndex != newIndex) {
                    val level =
                        when (newIndex) {
                            0 -> ActiveLevel.ACTIVE
                            1 -> ActiveLevel.MODERATELY
                            else -> ActiveLevel.SEDENTARY
                        }
                    profileViewModel.saveActiveLevelSetting(level)
                }
            }

            editWeightIv.setOnClickListener {
                showEditProfileDialog()
            }

            editHeightIv.setOnClickListener {
                showEditProfileDialog()
            }
        }
    }

    private fun showEditProfileDialog() {
        EditProfileDialogFragment.display(
            childFragmentManager,
            defaultValues,
            ::onActionSaveProfile,
        )
    }

    private fun onActionSaveProfile(
        weight: Double,
        height: Double,
    ) {
        profileViewModel.saveUserWeight(weight)
        profileViewModel.saveUserHeight(height)
    }

    private fun handleUserData(userData: Triple<ActiveLevel, Double, Double>) {
        handleActionLevel(userData.first)
        showHeight(userData.third)
        showWeight(userData.second)
    }

    private fun handleActionLevel(level: ActiveLevel) {
        binding.selectActionLevel.apply {
            when (level) {
                ActiveLevel.ACTIVE -> selectItemByIndex(0)
                ActiveLevel.MODERATELY -> selectItemByIndex(1)
                ActiveLevel.SEDENTARY -> selectItemByIndex(2)
            }
        }
    }

    private fun handleLanguageChange(language: Language) {
        currentLanguage = language
        setSwitchLanguage(language)
    }

    private fun setSwitchLanguage(language: Language) {
        binding.switchTranslate.apply {
            isChecked = language != Language.ENGLISH
        }
    }

    private fun applyLanguage(isEnglish: Boolean) {
        if (isEnglish) {
            profileViewModel.saveLanguageSetting(Language.ENGLISH)
        } else {
            profileViewModel.saveLanguageSetting(Language.INDONESIA)
        }
    }

    private fun showHeight(height: Double) {
        binding.userHeightTv.text = getString(R.string.label_value_height, height.toString())
        defaultValues = EditProfileDialogFragment.DefaultValues(defaultValues.weight, height)
    }

    private fun showWeight(weight: Double) {
        binding.userWeightTv.text = getString(R.string.label_value_weight, weight.toString())
        defaultValues = EditProfileDialogFragment.DefaultValues(weight, defaultValues.height)
    }

    private fun onSignOutResult(result: ResultState<String>) {
        when (result) {
            is ResultState.Loading -> showLoading(true)
            is ResultState.Success -> showLoading(false)
            is ResultState.Error -> {
                showLoading(false)
                binding.root.showSnackBar(result.message)
            }
        }
    }

    private fun showLoading(status: Boolean) {
        binding.loadingLayout.root.apply {
            if (status) show() else gone()
        }
    }
}
