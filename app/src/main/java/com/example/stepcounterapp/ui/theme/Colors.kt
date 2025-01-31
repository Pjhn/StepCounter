package com.example.stepcounterapp.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

object Colors {
    val DarkColorScheme = darkColorScheme(
        primary = Color(0xFFBB86FC),
        secondary = Color(0xFF03DAC6),
        tertiary = Color(0xFF3700B3),
        background = Color.Black,
        surface = Color.DarkGray,
        error = Color.Red,
        onPrimary = Color.Black,
        onSecondary = Color.White,
        onBackground = Color.White,
        onSurface = Color.White,
        onError = Color.White
    )

    val LightColorScheme = lightColorScheme(
        primary = Color(0xFF4D61D7),
        secondary = Color(0xFFE3EAFF),
        tertiary = Color(0xFF4D61D7),
        background = Color(0xFFFCFCFC),
        surface = Color.DarkGray,
        error = Color.Red,
        onPrimary = Color(0xFF363636),
        onSecondary = Color(0xFF363636),
        onBackground = Color(0xFF363636),
        onSurface = Color(0xFF363636),
        onError = Color.White,
        outline = Color(0xFF363636)
    )
}