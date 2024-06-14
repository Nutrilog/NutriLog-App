package com.nutrilog.app.utils.helpers

import android.util.Patterns
import com.nutrilog.app.domain.model.Gender
import com.nutrilog.app.utils.constant.AppConstant
import kotlin.math.pow
import kotlin.math.roundToInt

val String.isEmailCorrect: Boolean
    get() = Patterns.EMAIL_ADDRESS.matcher(this).matches()

val String.isFullNameCorrect: Boolean
    get() = !Regex("[^a-zA-Z ]").containsMatchIn(this)

val String.isLengthPasswordCorrect: Boolean
    get() = this.length >= AppConstant.PASSWORD_LENGTH

fun String.convertGender(): Gender {
    return when (this.lowercase()) {
        "male" -> Gender.MALE
        "female" -> Gender.FEMALE
        else -> throw IllegalArgumentException("Unknown gender: $this")
    }
}

fun Double.roundToDecimalPlaces(decimalPlaces: Int): Double {
    val factor = 10.0.pow(decimalPlaces)
    return (this * factor).roundToInt() / factor
}

fun String.capitalizeWords(delimiter: String = " ") =
    split(delimiter).joinToString(delimiter) { word ->
        val smallCaseWord = word.lowercase()
        smallCaseWord.replaceFirstChar(Char::titlecaseChar)
    }

val String.isValidateGender: Boolean
    get() = this.lowercase() == Gender.MALE.label || this.lowercase() == Gender.FEMALE.label
