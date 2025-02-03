package com.example.stepcounterapp.features.common.model

import kotlin.time.Duration

data class UserRecord(
    val stepCount: Int? = 0,
    val distance: Double? = 0.0,
    val calories: Double? = 0.0,
    val measurementTime: Duration = Duration.ZERO
)
