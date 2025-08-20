package com.pjhn.stepcounter.features.main.presentation.output

sealed class SensorState {
    object DelayLow: SensorState()
    object DelayHigh: SensorState()
}