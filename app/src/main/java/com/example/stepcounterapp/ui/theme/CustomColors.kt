package com.example.stepcounterapp.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color
import com.github.mikephil.charting.model.GradientColor

data class CustomColors (
    val material: ColorScheme,
    val tertiary: Color = material.primary,
    val onPrimaryAlt: Color = material.onPrimary,
    val success: Color = Color.Green,
    val checked: Color = Color.White,
    val unchecked: Color = Color.White,
    val text: Color = Color.Black,
    val outlinedButton: Color = Color.Black,
    val divider: Color = Color.DarkGray,
    val pauseButton: Color = Color.Blue,
    val brandColor: Color = Color.Blue,
    val defaultTextButton: Color = Color.DarkGray,
    val selectedTextButtonBackground: Color = Color.LightGray,
) {
    val primary: Color get() = material.primary
    val onPrimary: Color get() = material.onPrimary
    val primaryContainer: Color get() = material.primaryContainer
    val onPrimaryContainer: Color get() = material.onPrimaryContainer

    val secondary: Color get() = material.secondary
    val onSecondary: Color get() = material.onSecondary
    val secondaryContainer: Color get() = material.secondaryContainer
    val onSecondaryContainer: Color get() = material.onSecondaryContainer

    val background: Color get() = material.background
    val onBackground: Color get() = material.onBackground

    val surface: Color get() = material.surface
    val onSurface: Color get() = material.onSurface

    val error: Color get() = material.error
    val onError: Color get() = material.onError
}