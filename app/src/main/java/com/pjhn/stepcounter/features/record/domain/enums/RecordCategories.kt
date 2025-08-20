package com.pjhn.stepcounter.features.record.domain.enums

enum class RecordCategories {
    STEP,
    TIME,
    CALORIES,
    DISTANCE;

    override fun toString(): String {
        return when (this) {
            STEP -> "Steps"
            TIME -> "Time"
            CALORIES -> "Calories"
            DISTANCE -> "Distance"
        }
    }
}

