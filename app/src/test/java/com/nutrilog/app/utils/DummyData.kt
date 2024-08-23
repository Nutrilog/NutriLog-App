package com.nutrilog.app.utils

import com.nutrilog.app.core.domain.model.Gender
import com.nutrilog.app.core.domain.model.NutritionFood
import com.nutrilog.app.core.domain.model.User
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date

object DummyData {
    private val formatter = SimpleDateFormat("yyyy-MM-dd")

    const val idDummy = "user-brvr3OSuD1-tsiZf"
    const val nameDummy = "Nizar"
    const val emailDummy = "nizar@email.com"
    const val passwordDummy = "nizar123"
    val genderDummy = Gender.MALE
    val dateOfBirthDummy: Date = formatter.parse("2000-01-01")
    const val tokenDummy =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLWJydnIzT1N1RDEtdHNpWmYiLCJpYXQiOjE3MTQ5MDEzNDd9.KOVcCXjMRaUmX1M-qWlIdG24ekEVU7xs1R2g8wOhl-c"

    val userDummy = User(
        id = idDummy,
        name = nameDummy,
        email = emailDummy,
        gender = genderDummy,
        dateOfBirth = dateOfBirthDummy.toString(),
        token = tokenDummy
    )

    val idFoodDummy = "food-brvr3OSuD1-tsiZf"
    val foodNameDummy = "Nasi Putih"
    val carbohydrateDummy = 14f
    val proteinsDummy = 0.3f
    val fatDummy = 0.2f
    val caloriesDummy = 52f

    val nutritionDummy = NutritionFood(
        id = idFoodDummy,
        foodName = foodNameDummy,
        carbohydrates = carbohydrateDummy,
        protein = proteinsDummy,
        fat = fatDummy,
        calories = caloriesDummy
    )
}