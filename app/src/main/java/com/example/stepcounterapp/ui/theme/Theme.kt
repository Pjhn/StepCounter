package com.example.stepcounterapp.ui.theme

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LocalColors = staticCompositionLocalOf { ColorSet.Light.lightColors }

@Composable
fun StepCounterAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    typography: Typography = Typography,
    shapes: Shapes = Shapes,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> ColorSet.Black.darkColors
        else -> ColorSet.Light.lightColors
    }

    val view = LocalView.current
    val context = LocalContext.current
    val activity = context.findActivity()

    if (activity != null && !view.isInEditMode) {
        SideEffect {
            val window = activity.window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    CompositionLocalProvider(
        LocalColors provides colorScheme
    ) {
        MaterialTheme(
            colorScheme = colorScheme.material,
            typography = typography,
            shapes = shapes,
            content = content
        )
    }
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

val MaterialTheme.colors: CustomColors
    @Composable
    @ReadOnlyComposable
    get() = LocalColors.current