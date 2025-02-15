package com.example.stepcounterapp.features.main.presentation.output

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface IMainViewModelOutput {
    val mainState: StateFlow<MainState>
    val mainUiEffect: SharedFlow<MainUiEffect>
}

sealed class MainUiEffect {
    object OpenRecord : MainUiEffect()

    object StartMeasurement : MainUiEffect()

    object PauseMeasurement : MainUiEffect()
}