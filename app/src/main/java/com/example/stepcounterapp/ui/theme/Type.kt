package com.example.stepcounterapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.stepcounterapp.R

private val notoSansBold = FontFamily(
    Font(R.font.noto_sans_kr_bold, FontWeight.Bold)
)
private val notoSansRegular = FontFamily(
    Font(R.font.noto_sans_kr_regular, FontWeight.Normal)
)
private val notoSansMedium = FontFamily(
    Font(R.font.noto_sans_kr_medium, FontWeight.Medium)
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = notoSansBold,
        fontSize = 60.sp
    ),
    displayMedium = TextStyle(
        fontFamily = notoSansBold,
        fontSize = 48.sp
    ),
    displaySmall = TextStyle(
        fontFamily = notoSansBold,
        fontSize = 36.sp
    ),

    headlineLarge = TextStyle(
        fontFamily = notoSansBold,
        fontSize = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = notoSansBold,
        fontSize = 28.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = notoSansBold,
        fontSize = 24.sp
    ),

    titleLarge = TextStyle(
        fontFamily = notoSansMedium,
        fontSize = 22.sp
    ),
    titleMedium = TextStyle(
        fontFamily = notoSansMedium,
        fontSize = 16.sp
    ),
    titleSmall = TextStyle(
        fontFamily = notoSansMedium,
        fontSize = 14.sp
    ),

    bodyLarge = TextStyle(
        fontFamily = notoSansRegular,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = notoSansRegular,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = notoSansRegular,
        fontSize = 12.sp
    ),

    labelLarge = TextStyle(
        fontFamily = notoSansMedium,
        fontSize = 14.sp
    ),
    labelMedium = TextStyle(
        fontFamily = notoSansMedium,
        fontSize = 12.sp
    ),
    labelSmall = TextStyle(
        fontFamily = notoSansRegular,
        fontSize = 10.sp
    )
)
