package com.nutrilog.app.presentation.ui.base.component.bottomSheet.nutrition

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nutrilog.app.R
import com.nutrilog.app.databinding.NutritionCardSquareBinding
import com.nutrilog.app.domain.model.NutritionOption

class NutritionBottomSheetAdapter :
    RecyclerView.Adapter<NutritionBottomSheetAdapter.NutritionDataViewHolder>() {
    private var nutritionDataList: List<Pair<NutritionOption, Double>> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): NutritionBottomSheetAdapter.NutritionDataViewHolder {
        val binding =
            NutritionCardSquareBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NutritionDataViewHolder(binding, parent.context)
    }

    override fun getItemCount(): Int = nutritionDataList.size

    override fun onBindViewHolder(
        holder: NutritionBottomSheetAdapter.NutritionDataViewHolder,
        position: Int,
    ) {
        val (nutritionOption, amount) = nutritionDataList[position]
        holder.bind(nutritionOption, amount)
    }

    fun setNutritionData(data: Map<NutritionOption, Double>) {
        val newList = data.toList()
        val diffResult =
            DiffUtil.calculateDiff(
                NutritionDiffCallback(
                    nutritionDataList,
                    newList,
                ),
            )
        nutritionDataList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    inner class NutritionDataViewHolder(
        val binding: NutritionCardSquareBinding,
        private val context: Context,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            nutritionOption: NutritionOption,
            amount: Double,
        ) {
            with(binding) {
                nutritionTypeTV.text = nutritionOption.label

                val formattedAmount =
                    context.getString(
                        R.string.label_amount_of_nutrition,
                        amount.toString(),
                    )

                val spannableString = SpannableString(formattedAmount)
                val start = formattedAmount.indexOf('g')
                val end = start + 1

                spannableString.setSpan(
                    RelativeSizeSpan(0.7f),
                    start,
                    end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE,
                )
                totalNutritionTV.text = spannableString
            }
        }
    }

    private class NutritionDiffCallback(
        private val oldList: List<Pair<NutritionOption, Double>>,
        private val newList: List<Pair<NutritionOption, Double>>,
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(
            oldItemPosition: Int,
            newItemPosition: Int,
        ): Boolean {
            return oldList[oldItemPosition].first == newList[newItemPosition].first
        }

        override fun areContentsTheSame(
            oldItemPosition: Int,
            newItemPosition: Int,
        ): Boolean {
            return oldList[oldItemPosition].second == newList[newItemPosition].second
        }
    }
}
