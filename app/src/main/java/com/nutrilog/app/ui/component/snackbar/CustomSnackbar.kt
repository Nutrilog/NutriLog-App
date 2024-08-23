package com.nutrilog.app.ui.component.snackbar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.nutrilog.app.R
import com.nutrilog.app.utils.helpers.findSuitableParent

class CustomSnackbar(
    parent: ViewGroup,
    content: CustomSnackbarView,
) : BaseTransientBottomBar<CustomSnackbar>(parent, content, content) {
    private lateinit var closeButton: Button

    init {
        getView().setBackgroundColor(
            ContextCompat.getColor(
                view.context,
                R.color.transparent,
            ),
        )
        getView().setPadding(0, 0, 0, 0)
    }

    override fun show() {
        super.show()

        closeButton.setOnClickListener {
            dismiss()
        }
    }

    private fun setCloseButton(view: View) {
        closeButton = view.findViewById(R.id.closeSnackbar)
    }

    companion object {
        fun make(
            view: View,
            message: String,
            duration: Int,
        ): CustomSnackbar {
            val parent =
                view.findSuitableParent() ?: throw IllegalArgumentException(
                    "No suitable parent found from the given view. Please provide a valid view.",
                )

            val customView =
                LayoutInflater.from(view.context).inflate(
                    R.layout.layout_custom_snackbar,
                    parent,
                    false,
                ) as CustomSnackbarView

            val messageView = customView.findViewById<TextView>(R.id.messageSnackbar)
            messageView.text = message

            val snackbar = CustomSnackbar(parent, customView)
            snackbar.duration = duration
            snackbar.setCloseButton(customView)
            return snackbar
        }
    }
}
