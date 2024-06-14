package com.nutrilog.app.presentation.ui.main.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nutrilog.app.R
import com.nutrilog.app.databinding.NutritionCardBinding
import com.nutrilog.app.domain.model.ActiveLevel
import com.nutrilog.app.domain.model.NutritionLevel
import com.nutrilog.app.domain.model.NutritionOption
import com.nutrilog.app.utils.helpers.formatNutritionAmount
import com.nutrilog.app.utils.helpers.getBMR
import com.nutrilog.app.utils.helpers.getCalorieLimit
import com.nutrilog.app.utils.helpers.getLimitNutrition

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.NutritionDataViewHolder>() {
    private var nutritionDataList: List<Pair<NutritionOption, Double>> = listOf()
    private var userAge: Int = 0
    private var userWeight: Double = 0.0
    private var userHeight: Double = 0.0
    private var userGender: String = "male"
    private var userActiveLevel: ActiveLevel = ActiveLevel.SEDENTARY

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): NutritionDataViewHolder {
        val binding =
            NutritionCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NutritionDataViewHolder(binding, parent.context)
    }

    override fun getItemCount(): Int = nutritionDataList.size

    override fun onBindViewHolder(
        holder: NutritionDataViewHolder,
        position: Int,
    ) {
        val (nutritionOption, amount) = nutritionDataList[position]
        holder.bind(nutritionOption, amount)
    }

    fun setNutritionData(data: Map<NutritionOption, Double>) {
        val newList = data.toList()
        val diffResult = DiffUtil.calculateDiff(NutritionDiffCallback(nutritionDataList, newList))
        nutritionDataList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    fun setUserData(
        age: Int,
        gender: String,
    ) {
        val isChanged = userAge != age || userGender != gender
        userAge = age
        userGender = gender
        notifyChanges(isChanged)
    }

    fun setUserData(
        activeLevel: ActiveLevel,
        weight: Double,
        height: Double,
    ) {
        val isChanged =
            userActiveLevel != activeLevel || userWeight != weight || userHeight != height
        userActiveLevel = activeLevel
        userWeight = weight
        userHeight = height
        notifyChanges(isChanged)
    }

    private fun notifyChanges(isChange: Boolean) {
        if (isChange) {
            notifyItemRangeChanged(0, nutritionDataList.size)
        }
    }

    inner class NutritionDataViewHolder(
        val binding: NutritionCardBinding,
        private val context: Context,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            nutritionOption: NutritionOption,
            amount: Double,
        ) {
            with(binding) {
                nutritionTypeTV.text =
                    when (nutritionOption.label) {
                        "Calories" -> context.getString(R.string.label_nutrition_calories)
                        "Protein" -> context.getString(R.string.label_nutrition_protein)
                        "Fat" -> context.getString(R.string.label_nutrition_fat)
                        else -> context.getString(R.string.label_nutrition_carbs)
                    }

                val getBMR = getBMR(userAge, userWeight, userHeight, userGender)
                val getLimitCalorie = getCalorieLimit(getBMR, userActiveLevel)
                val nutritionLimit = getLimitNutrition(nutritionOption, getLimitCalorie)
                val nutritionLevel = determineNutritionLevel(amount, nutritionLimit)

                nutritionLevelTV.text =
                    when (nutritionLevel.label) {
                        "Deficient" -> context.getString(R.string.label_level_deficient)
                        "Optimal" -> context.getString(R.string.label_level_optimal)
                        "Close to limit" -> context.getString(R.string.label_level_close)
                        else -> context.getString(R.string.label_level_over)
                    }

                totalNutritionTV.text =
                    formatNutritionAmount(context, amount, nutritionOption.label == "Calories")

                setBgCard(nutritionLevel)
            }
        }

        private fun determineNutritionLevel(
            amount: Double,
            limit: List<Double>,
        ): NutritionLevel {
            return when {
                amount < limit[0] -> NutritionLevel.DEFICIENT
                amount > limit[1] -> NutritionLevel.CLOSE
                amount >= limit[0] && amount <= limit[1] -> NutritionLevel.OPTIMAL
                else -> NutritionLevel.OVER
            }
        }

        private fun setBgCard(nutritionLevel: NutritionLevel) {
            val drawable =
                when (nutritionLevel) {
                    NutritionLevel.OPTIMAL ->
                        ContextCompat.getDrawable(
                            binding.cardLayoutOne.context,
                            R.drawable.bg_card_2,
                        )

                    NutritionLevel.CLOSE ->
                        ContextCompat.getDrawable(
                            binding.cardLayoutOne.context,
                            R.drawable.bg_card_3,
                        )

                    else ->
                        ContextCompat.getDrawable(
                            binding.cardLayoutOne.context,
                            R.drawable.bg_card_1,
                        )
                }
            binding.cardLayoutOne.background = drawable
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
