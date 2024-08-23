package com.nutrilog.app.ui.screen.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.nutrilog.app.R
import com.nutrilog.app.ui.base.BaseFragment
import com.nutrilog.app.ui.component.snackbar.CustomSnackbar
import com.nutrilog.app.core.domain.common.ResultState
import com.nutrilog.app.databinding.FragmentSignInBinding
import com.nutrilog.app.ui.MainActivity
import com.nutrilog.app.utils.constant.AppConstant.PASSWORD_LENGTH
import com.nutrilog.app.utils.helpers.gone
import com.nutrilog.app.utils.helpers.isLengthPasswordCorrect
import com.nutrilog.app.utils.helpers.observe
import com.nutrilog.app.utils.helpers.show
import com.nutrilog.app.utils.helpers.showSnackBar
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInFragment : BaseFragment<FragmentSignInBinding>() {
    private val authViewModel: AuthViewModel by viewModel()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSignInBinding =
        FragmentSignInBinding::inflate

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            tvSignup.setOnClickListener {
                moveToSignUp()
            }

            btnSignin.setOnClickListener {
                signInRequest()
            }
        }
    }

    private fun signInRequest() {
        val email = binding.edLoginEmail.text.toString()
        val password = binding.edLoginPassword.text.toString()
        when {
            email.isEmpty() ->
                binding.edLoginEmail.error =
                    getString(R.string.validation_must_not_empty)

            password.isEmpty() ->
                binding.edLoginPassword.error =
                    getString(R.string.validation_must_not_empty)

            !password.isLengthPasswordCorrect ->
                binding.edLoginPassword.error =
                    String.format(getString(R.string.validation_password), PASSWORD_LENGTH)

            else -> {
                signInProcess(email, password)
            }
        }
    }

    private fun signInProcess(
        email: String,
        password: String,
    ) {
        observe(authViewModel.signIn(email, password), ::showResultSignIn)
    }

    private fun showResultSignIn(result: ResultState<String>) {
        when (result) {
            is ResultState.Loading -> showLoading(true)

            is ResultState.Success -> {
                showLoading(false)
                binding.root.showSnackBar(
                    getString(R.string.message_signin_success),
                    Snackbar.LENGTH_SHORT,
                ) {
                    addCallback(
                        object : BaseTransientBottomBar.BaseCallback<CustomSnackbar>() {
                            override fun onDismissed(
                                transientBottomBar: CustomSnackbar?,
                                event: Int,
                            ) {
                                moveToMain()
                            }
                        },
                    )
                }
            }

            is ResultState.Error -> {
                showLoading(false)
                binding.root.showSnackBar(getString(R.string.message_signin_failed))
            }
        }
    }

    private fun moveToMain() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun moveToSignUp() {
        findNavController().navigate(
            SignInFragmentDirections.actionSignInFragmentToSignUpFragment(),
        )
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingLayout.root.apply {
            if (isLoading) show() else gone()
        }
    }
}
