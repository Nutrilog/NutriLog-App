package com.nutrilog.app.utils.helpers

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import com.nutrilog.app.R
import com.nutrilog.app.core.domain.model.ActiveLevel
import com.nutrilog.app.core.domain.model.ActivityFactor
import com.nutrilog.app.core.domain.model.Nutrition
import com.nutrilog.app.core.domain.model.NutritionOption

fun convertListToNutritionLevel(list: List<Nutrition>): Map<NutritionOption, Double> {
    return NutritionOption.entries.associateWith { option ->
        list.sumOf { nutrition ->
            when (option) {
                NutritionOption.CARBOHYDRATE -> nutrition.carbohydrate.toDouble()
                NutritionOption.PROTEIN -> nutrition.proteins.toDouble()
                NutritionOption.FAT -> nutrition.fat.toDouble()
                NutritionOption.CALORIES -> nutrition.calories.toDouble()
            }
        }
    }
}

fun convertToNutritionLevel(list: Nutrition): Map<NutritionOption, Double> {
    return NutritionOption.entries.associateWith { option ->
        when (option) {
            NutritionOption.CARBOHYDRATE -> list.carbohydrate.toDouble()
            NutritionOption.PROTEIN -> list.proteins.toDouble()
            NutritionOption.FAT -> list.fat.toDouble()
            NutritionOption.CALORIES -> list.calories.toDouble()
        }
    }
}

fun sortNutritionByCreatedAtDescending(nutritionList: List<Nutrition>): List<Nutrition> {
    return nutritionList.sortedByDescending { it.createdAt }
}

fun formatNutritionAmount(
    context: Context,
    amount: Double,
    isCalories: Boolean,
): SpannableString {
    val unit =
        if (isCalories) context.getString(R.string.label_calories) else context.getString(R.string.label_gram)
    val stringId =
        if (isCalories) R.string.label_amount_of_nutrition_calories else R.string.label_amount_of_nutrition
    val formattedAmount = context.getString(stringId, amount.roundToDecimalPlaces(1))
    val spannableString = SpannableString(formattedAmount)
    val start = formattedAmount.indexOf(unit)
    val end = start + unit.length
    spannableString.setSpan(RelativeSizeSpan(0.7f), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

    return spannableString
}

fun getBMR(
    age: Int,
    weight: Double,
    height: Double,
    gender: String,
): Double {
    return when (gender) {
        "male" -> (10.0 * weight) + (6.25 * height) - (5.0 * age.toDouble()) + 5.0
        else -> (10.0 * weight) + (6.25 * height) - (5.0 * age.toDouble()) - 161.0
    }
}

fun getCalorieLimit(
    bmr: Double,
    activeLvl: ActiveLevel,
): Double {
    return when (activeLvl.value) {
        "Active" -> ActivityFactor.ACTIVE.label * bmr
        "Moderately Active" -> ActivityFactor.MODERATE.label * bmr
        else -> ActivityFactor.SEDENTARY.label * bmr
    }
}

fun getLimitNutrition(
    nutritionOption: NutritionOption,
    calories: Double,
): List<Double> {
    val caloriesMin = calories - 200.0
    val caloriesMax = calories + 200.0

    val percentage =
        when (nutritionOption.label) {
            "Carbohydrate" -> 0.50
            "Protein" -> 0.20
            else -> 0.30
        }

    val kcalToGrams =
        when (nutritionOption.label) {
            "Fat" -> 9.0
            else -> 4.0
        }

    val kcalNutritionMinimal = caloriesMin * percentage
    val gramNutritionMinimal = (kcalNutritionMinimal) / kcalToGrams

    val kcalNutritionOptimal = calories * percentage
    val gramNutritionOptimal = kcalNutritionOptimal / kcalToGrams

    val kcalNutritionMaximal = caloriesMax * percentage
    val gramNutritionMaximal = (kcalNutritionMaximal) / kcalToGrams

    return when (nutritionOption.label) {
        "Calories" ->
            listOf(
                caloriesMin,
                calories,
                caloriesMax,
            )

        else ->
            listOf(
                gramNutritionMinimal,
                gramNutritionOptimal,
                gramNutritionMaximal,
            )
    }
}
