package com.pjhn.stepcounter.features.common.model.enums

enum class Duration {
    WEEK,
    MONTH,
    YEAR;

    override fun toString(): String {
        return when(this){
            WEEK -> "W"
            MONTH -> "M"
            YEAR -> "Y"
        }
    }
}