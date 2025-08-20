package com.pjhn.stepcounter.features.common.model

import java.time.LocalDate
import kotlin.time.Duration

data class StepRecord(
    val date: LocalDate? = null,
    val stepCount: Int? = 0,
    val distance: Double? = 0.0,
    val calories: Double? = 0.0,
    val measurementTime: Duration? = Duration.ZERO
)
