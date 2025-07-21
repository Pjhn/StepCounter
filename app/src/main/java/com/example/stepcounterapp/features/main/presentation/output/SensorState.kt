package com.example.stepcounterapp.features.main.presentation.output

sealed class SensorState {
    object DelayLow: SensorState()
    object DelayHigh: SensorState()
}