package com.nutrilog.app.core.domain.model

enum class NutritionOption(val label: String) {
    CARBOHYDRATE("Carbohydrate"),
    PROTEIN("Protein"),
    FAT("Fat"),
    CALORIES("Calories")
}

enum class NutritionLevel(val label: String) {
    OPTIMAL("Optimal"),
    CLOSE("Close to limit"),
    DEFICIENT("Deficient"),
    OVER("Excessively high")
}

enum class ActivityFactor(val label: Double) {
    SEDENTARY(1.2),
    MODERATE(1.55),
    ACTIVE(1.725)
}
