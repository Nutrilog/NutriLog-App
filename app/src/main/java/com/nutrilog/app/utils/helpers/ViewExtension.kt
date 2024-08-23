package com.nutrilog.app.utils.helpers

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import com.nutrilog.app.ui.component.snackbar.CustomSnackbar
import com.skydoves.powerspinner.PowerSpinnerView

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun PowerSpinnerView.setMonth(currentMonth: Int?) {
    currentMonth?.also { this.selectItemByIndex(it) }
}

fun View.showSnackBar(
    message: String,
    duration: Int = Snackbar.LENGTH_LONG,
    callback: (CustomSnackbar.() -> Unit)? = null,
) {
    val snackbar =
        CustomSnackbar.make(
            this,
            message,
            duration,
        )

    callback?.invoke(snackbar)

    snackbar.show()
}

fun View?.findSuitableParent(): ViewGroup? {
    var view = this
    var fallback: ViewGroup? = null
    do {
        if (view is CoordinatorLayout) {
            return view
        } else if (view is FrameLayout) {
            if (view.id == android.R.id.content) {
                return view
            } else {
                fallback = view
            }
        }

        if (view != null) {
            val parent = view.parent
            view = if (parent is View) parent else null
        }
    } while (view != null)

    return fallback
}
