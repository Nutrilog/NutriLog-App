package com.nutrilog.app.core.data.remote.request

import com.google.gson.annotations.SerializedName

data class SaveNutritionRequest(
    @SerializedName("food_name")
    val foodName: String,
    val carbohydrate: Float,
    val proteins: Float,
    val fat: Float,
    val calories: Float,
)
