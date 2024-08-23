package com.nutrilog.app.ui.component.input

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.textfield.TextInputLayout
import com.nutrilog.app.R
import com.nutrilog.app.utils.helpers.dpToPx

class InputLayout : TextInputLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr,
    )

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        boxBackgroundMode = BOX_BACKGROUND_OUTLINE
        isExpandedHintEnabled = false

        val corner = CORNER_RADIUS.dpToPx(context)
        val shapeAppearanceModel =
            ShapeAppearanceModel.builder()
                .setAllCorners(CornerFamily.ROUNDED, corner)
                .build()
        setShapeAppearanceModel(shapeAppearanceModel)

        setHintTextAppearance(R.style.Text_Body2_Bold)
        setHelperTextTextAppearance(R.style.Text_Body2_Bold)
        setCounterOverflowTextAppearance(R.style.Text_Body2_Bold)
        setCounterTextAppearance(R.style.Text_Body2_Bold)
        placeholderTextAppearance = R.style.Text_Caption
        placeholderTextColor = context.resources.getColorStateList(R.color.placeholder_colors, null)
    }

    companion object {
        private const val CORNER_RADIUS = 12
    }
}
