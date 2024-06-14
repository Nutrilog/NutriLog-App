package com.nutrilog.app.presentation.ui.base.component.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.nutrilog.app.R
import com.nutrilog.app.databinding.LayoutPhotoDialogBinding

class PhotoDialogFragment(
    private val onCameraClick: () -> Unit,
    private val onGalleryClick: () -> Unit,
) : DialogFragment() {
    private var _binding: LayoutPhotoDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = LayoutPhotoDialogBinding.inflate(inflater, container, false)

        return _binding?.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.apply {
            window?.setBackgroundDrawableResource(R.drawable.bg_dialog_transparent)
        }
        initActions()
    }

    private fun initActions() {
        binding.apply {
            btnClose.setOnClickListener {
                dismiss()
            }

            containerCamera.setOnClickListener {
                onCameraClick.invoke()
                dismiss()
            }

            containerGallery.setOnClickListener {
                onGalleryClick.invoke()
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG: String = "PhotoDialogFragment"

        fun display(
            fragmentManager: FragmentManager,
            onCameraClick: () -> Unit,
            onGalleryClick: () -> Unit,
        ): PhotoDialogFragment {
            val photoDialog = PhotoDialogFragment(onCameraClick, onGalleryClick)
            photoDialog.show(fragmentManager, TAG)
            return photoDialog
        }
    }
}
