package com.nutrilog.app.ui.component.snackbar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.ContentViewCallback
import com.nutrilog.app.R

class CustomSnackbarView : ConstraintLayout, ContentViewCallback {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr,
    ) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.item_custom_snackbar, this)
        clipToPadding = false
    }

    override fun animateContentIn(
        delay: Int,
        duration: Int,
    ) {
        alpha = 0f
        animate().alpha(1f).setDuration(duration.toLong()).setStartDelay(delay.toLong()).start()
    }

    override fun animateContentOut(
        delay: Int,
        duration: Int,
    ) {
        alpha = 1f
        animate().alpha(0f).setDuration(duration.toLong()).setStartDelay(delay.toLong()).start()
    }
}
