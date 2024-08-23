package com.nutrilog.app.core.data.remote.request

import com.google.gson.annotations.SerializedName

data class NutritionFoodRequest(
    @field:SerializedName("food_name")
    val foodName: String,
)
