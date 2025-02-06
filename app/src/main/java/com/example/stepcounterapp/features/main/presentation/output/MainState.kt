package com.example.stepcounterapp.features.main.presentation.output

import com.example.stepcounterapp.features.common.model.UserRecord

sealed class MainState {
    object Loading : MainState()

    object Measuring : MainState()

    object Main : MainState()

    class Failed(
        val reason: String
    ) : MainState()
}