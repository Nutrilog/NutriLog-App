package com.nutrilog.app.presentation.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.nutrilog.app.R
import com.nutrilog.app.databinding.FragmentSignUpBinding
import com.nutrilog.app.domain.common.ResultState
import com.nutrilog.app.domain.model.Gender
import com.nutrilog.app.presentation.ui.base.BaseFragment
import com.nutrilog.app.presentation.ui.base.component.snackbar.CustomSnackbar
import com.nutrilog.app.utils.constant.AppConstant.PASSWORD_LENGTH
import com.nutrilog.app.utils.helpers.convertDateLocaleToString
import com.nutrilog.app.utils.helpers.convertStringLocaleToDate
import com.nutrilog.app.utils.helpers.getCalender
import com.nutrilog.app.utils.helpers.getTimeInMillis
import com.nutrilog.app.utils.helpers.gone
import com.nutrilog.app.utils.helpers.isLengthPasswordCorrect
import com.nutrilog.app.utils.helpers.isValidateGender
import com.nutrilog.app.utils.helpers.observe
import com.nutrilog.app.utils.helpers.show
import com.nutrilog.app.utils.helpers.showSnackBar
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar
import java.util.Date

class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {
    private val authViewModel: AuthViewModel by viewModel()

    private var selectDate: Long? = null

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSignUpBinding =
        FragmentSignUpBinding::inflate

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            tvSignin.setOnClickListener {
                backSignIn()
            }

            btnSignup.setOnClickListener {
                signUpRequest()
            }

            edRegisterDateBirth.apply {
                keyListener = null
                setOnTouchListener { _, motionEvent ->
                    when (motionEvent.action) {
                        MotionEvent.ACTION_DOWN -> {
                            openDatePicker()
                        }

                        MotionEvent.ACTION_UP -> {
                            view.performClick()
                        }
                    }
                    true
                }
                setOnClickListener { openDatePicker() }
            }
        }
    }

    private fun openDatePicker() {
        val dateObject = selectDate?.let { Date(it) } ?: Date()
        val (day, month, year) = dateObject.getCalender(locale)
        val dateMilliseconds = getTimeInMillis(day, month, year)

        val constraints =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointBackward.before(Calendar.getInstance().timeInMillis))
                .build()

        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date of birth")
                .setSelection(dateMilliseconds)
                .setCalendarConstraints(constraints)
                .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            selectDate = selection
            val date = Date(selection)
            val format = date.convertDateLocaleToString()
            binding.edRegisterDateBirth.setText(format)
        }

        datePicker.show(parentFragmentManager, "datePicker")
    }

    private fun signUpRequest() {
        val name = binding.edRegisterName.text.toString()
        val email = binding.edRegisterEmail.text.toString()
        val password = binding.edRegisterPassword.text.toString()
        val gender = binding.edRegisterGender.text.toString()
        val dateOfBirth = binding.edRegisterDateBirth.text.toString()

        when {
            name.isEmpty() ->
                binding.edRegisterName.error =
                    getString(R.string.validation_must_not_empty)

            email.isEmpty() ->
                binding.edRegisterEmail.error =
                    getString(R.string.validation_must_not_empty)

            password.isEmpty() ->
                binding.edRegisterPassword.error =
                    getString(R.string.validation_must_not_empty)

            !password.isLengthPasswordCorrect ->
                binding.edRegisterPassword.error =
                    String.format(
                        getString(
                            R.string.validation_password,
                        ),
                        PASSWORD_LENGTH,
                    )

            gender.isEmpty() ->
                binding.edRegisterGender.error =
                    getString(R.string.validation_must_not_empty)

            !gender.isValidateGender ->
                binding.edRegisterGender.error =
                    getString(R.string.validation_gender)

            dateOfBirth.isEmpty() ->
                binding.edRegisterDateBirth.error =
                    getString(R.string.validation_must_not_empty)

            else -> {
                val genderEnum =
                    Gender.entries.find { it.label == gender.lowercase() }
                        ?: throw IllegalArgumentException("No enum constant for gender: $gender")
                val convertDOB = dateOfBirth.convertStringLocaleToDate()

                signUpProcess(name, email, password, genderEnum, convertDOB)
            }
        }
    }

    private fun signUpProcess(
        name: String,
        email: String,
        password: String,
        gender: Gender,
        dateOfBirth: Date,
    ) {
        observe(
            authViewModel.signUp(name, email, password, gender, dateOfBirth),
            ::showResultSignUp,
        )
    }

    private fun showResultSignUp(result: ResultState<String>) {
        when (result) {
            is ResultState.Loading -> showLoading(true)

            is ResultState.Success -> {
                showLoading(false)
                binding.root.showSnackBar(
                    result.data,
                    Snackbar.LENGTH_SHORT,
                ) {
                    addCallback(
                        object : BaseTransientBottomBar.BaseCallback<CustomSnackbar>() {
                            override fun onDismissed(
                                transientBottomBar: CustomSnackbar?,
                                event: Int,
                            ) {
                                backSignIn()
                            }
                        },
                    )
                }
            }

            is ResultState.Error -> {
                showLoading(false)
                binding.root.showSnackBar(result.message)
            }
        }
    }

    private fun backSignIn() {
        findNavController().popBackStack()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingLayout.root.apply {
            if (isLoading) show() else gone()
        }
    }
}
