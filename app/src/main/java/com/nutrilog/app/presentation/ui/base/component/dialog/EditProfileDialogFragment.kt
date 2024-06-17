package com.nutrilog.app.presentation.ui.base.component.dialog

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.textfield.TextInputEditText
import com.nutrilog.app.R
import com.nutrilog.app.databinding.LayoutEditProfileBinding

class EditProfileDialogFragment(
    private val defaultValues: DefaultValues,
    private val onEditProfile: (weight: Double, height: Double) -> Unit,
) : DialogFragment() {
    private var _binding: LayoutEditProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = LayoutEditProfileBinding.inflate(inflater, container, false)

        return _binding?.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setWindowAnimations(R.style.SlideAnimation)
            setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme_FullScreenDialog)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            setDefaultText(etHeight, defaultValues.height.toString())
            setDefaultText(etWeight, defaultValues.weight.toString())
            toolbar.apply {
                setNavigationOnClickListener { dismiss() }
                title = getString(R.string.header_edit_profile_title)
                inflateMenu(R.menu.edit_profile_menu)
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.action_save -> {
                            val weight = etWeight.text.toString()
                            val height = etHeight.text.toString()

                            when {
                                weight.isEmpty() -> {
                                    etWeight.error = getString(R.string.validation_must_not_empty)
                                    return@setOnMenuItemClickListener true
                                }

                                height.isEmpty() -> {
                                    etHeight.error = getString(R.string.validation_must_not_empty)
                                    return@setOnMenuItemClickListener true
                                }

                                else -> {
                                    onEditProfile(weight.toDouble(), height.toDouble())
                                }
                            }
                        }
                    }
                    dismiss()
                    true
                }
            }
        }
    }

    private fun setDefaultText(
        editText: TextInputEditText,
        defaultText: String,
    ) {
        val editable: Editable = editText.editableText
        editable.clear()
        editable.insert(0, defaultText)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG: String = "EditProfileDialogFragment"

        fun display(
            fragmentManager: FragmentManager,
            defaultValues: DefaultValues,
            onEditProfile: (weight: Double, height: Double) -> Unit,
        ): EditProfileDialogFragment {
            val editProfile = EditProfileDialogFragment(defaultValues, onEditProfile)
            editProfile.show(fragmentManager, TAG)
            return editProfile
        }
    }

    data class DefaultValues(
        val weight: Double,
        val height: Double,
    )
}
