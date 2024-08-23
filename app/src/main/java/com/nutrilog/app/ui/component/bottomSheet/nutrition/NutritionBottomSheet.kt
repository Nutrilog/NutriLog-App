package com.nutrilog.app.ui.component.bottomSheet.nutrition

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nutrilog.app.core.domain.model.Nutrition
import com.nutrilog.app.databinding.LayoutNutritionBottomSheetBinding
import com.nutrilog.app.utils.GridSpacingItemDecoration
import com.nutrilog.app.utils.helpers.capitalizeWords
import com.nutrilog.app.utils.helpers.convertDateTimeLocaleToString
import com.nutrilog.app.utils.helpers.convertToNutritionLevel
import com.nutrilog.app.utils.helpers.dpToPx
import com.nutrilog.app.utils.helpers.gone
import com.nutrilog.app.utils.helpers.show

class NutritionBottomSheet(
    private val onActionSave: ((Nutrition) -> Unit)? = null,
) : BottomSheetDialogFragment() {
    private var _binding: LayoutNutritionBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val nutritionAdapter by lazy { NutritionBottomSheetAdapter() }

    private lateinit var nutrition: Nutrition
    private var isFooter: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            nutrition =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.getParcelable(ARG_NUTRITION, Nutrition::class.java)!!
                } else {
                    it.getParcelable(ARG_NUTRITION)!!
                }
            isFooter = it.getBoolean(ARG_IS_FOOTER, false)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = LayoutNutritionBottomSheetBinding.inflate(inflater, container, false)

        return _binding?.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        behavior()
        initViews()
        initRecyclerView()
        initData()
    }

    private fun initRecyclerView() {
        binding.rvNutritionList.apply {
            layoutManager = GridLayoutManager(context, NUMBER_OF_COLUMNS)
            addItemDecoration(
                GridSpacingItemDecoration(
                    NUMBER_OF_COLUMNS,
                    SPACING.dpToPx(context).toInt(),
                    INCLUDE_EDGE,
                ),
            )
            adapter = nutritionAdapter
        }
    }

    private fun initData() {
        val nutritionLevel = convertToNutritionLevel(nutrition)
        nutritionAdapter.setNutritionData(nutritionLevel)
    }

    private fun behavior() {
        val behavior = BottomSheetBehavior.from(binding.nutritionBottomSheet)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.saveFlags = BottomSheetBehavior.SAVE_FIT_TO_CONTENTS
        behavior.isDraggable = false
    }

    private fun initViews() {
        binding.apply {
            buttonClose.setOnClickListener { dismiss() }
            footerButtons.apply {
                if (isFooter) show() else gone()
            }

            textFoodName.text = nutrition.foodName.capitalizeWords()
            textDate.text = nutrition.createdAt.convertDateTimeLocaleToString()

            if (isFooter) {
                buttonCancel.setOnClickListener { dismiss() }
                buttonSave.setOnClickListener {
                    onActionSave?.invoke(nutrition)
                    dismiss()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "NutritionBottomSheet"
        const val NUMBER_OF_COLUMNS = 2
        const val SPACING = 14
        const val INCLUDE_EDGE = true

        private const val ARG_NUTRITION = "nutrition"
        private const val ARG_IS_FOOTER = "is_footer"

        fun newInstance(
            nutrition: Nutrition,
            isFooter: Boolean = false,
            onActionSave: ((Nutrition) -> Unit)? = null,
        ) = NutritionBottomSheet(onActionSave)
            .apply {
            arguments =
                Bundle().apply {
                    putParcelable(ARG_NUTRITION, nutrition)
                    putBoolean(ARG_IS_FOOTER, isFooter)
                }
        }
    }
}
