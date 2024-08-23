package com.nutrilog.app.core.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@Entity(tableName = "nutrition")
data class Nutrition(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @field:SerializedName("user_id")
    @ColumnInfo(name = "user_id")
    val userId: String,
    @field:SerializedName("food_name")
    @ColumnInfo(name = "food_name")
    val foodName: String,
    val calories: Float,
    val proteins: Float,
    val carbohydrate: Float,
    val fat: Float,
    @field:SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    val createdAt: Date,
) : Parcelable

data class NutritionFood(
    val id: String,
    @field:SerializedName("food_name")
    val foodName: String,
    val carbohydrates: Float,
    val protein: Float,
    val fat: Float,
    val calories: Float,
)

@Parcelize
data class ResultNutrition(
    val foodName: String,
    val carbohydrate: Float,
    val proteins: Float,
    val fat: Float,
    val calories: Float,
) : Parcelable
