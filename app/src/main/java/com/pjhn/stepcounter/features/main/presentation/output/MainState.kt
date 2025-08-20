package com.pjhn.stepcounter.features.main.presentation.output

sealed class MainState {
    object Loading : MainState()

    object Measuring : MainState()

    object Main : MainState()

    class Failed(
        val reason: String
    ) : MainState()
}