package com.example.stepcounterapp.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

sealed class ColorSet {
    open lateinit var lightColors: CustomColors
    open lateinit var darkColors: CustomColors

    object Light : ColorSet(){
        override var lightColors = CustomColors(
            material = lightColorScheme(
                primary = Color(0xFF363636),
                secondary = Color(0xFFE3EAFF),
                tertiary = Color(0xFF4D61D7),
                background = Color(0xFFFCFCFC),
                surface = Color.DarkGray,
                error = Color.Red,
                onPrimary = Color(0xFFFCFCFC),
                onSecondary = Color(0xFF363636),
                onBackground = Color(0xFF363636),
                onSurface = Color(0xFF363636),
                onError = Color.White,
                outline = Color(0xFF363636)
            ),
            text= Color(0xFF363636),
            outlinedButton = Color(0XFFEFEFEF),
            divider = Color(0XFFEFEFEF),
            pauseButton = Color(0XFF4D61D7)
        )
    }

    object Black : ColorSet(){
        override var darkColors = CustomColors(
            material = darkColorScheme(
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
            ),
            text = Color.White
        )
    }
}