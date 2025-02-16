package com.example.stepcounterapp.features.record.domain.enums

enum class RecordCategories {
    STEP,
    DURATION,
    CALORIES,
    DISTANCE;

    override fun toString(): String {
        return when (this) {
            STEP -> "Steps"
            DURATION -> "Duration"
            CALORIES -> "Calories"
            DISTANCE -> "Distance"
        }
    }
}

