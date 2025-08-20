package com.pjhn.stepcounter.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.pjhn.stepcounter.R

private val dmSansBold = FontFamily(
    Font(R.font.dm_sans_bold, FontWeight.Bold)
)
private val dmSansRegular = FontFamily(
    Font(R.font.dm_sans_regular, FontWeight.Normal)
)
private val dmSansMedium = FontFamily(
    Font(R.font.dm_sans_medium, FontWeight.Medium)
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = dmSansRegular,
        fontSize = 48.sp
    ),
    displayMedium = TextStyle(
        fontFamily = dmSansRegular,
        fontSize = 36.sp
    ),
    displaySmall = TextStyle(
        fontFamily = dmSansRegular,
        fontSize = 18.sp
    ),

    headlineLarge = TextStyle(
        fontFamily = dmSansBold,
        fontSize = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = dmSansBold,
        fontSize = 28.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = dmSansBold,
        fontSize = 24.sp
    ),

    titleLarge = TextStyle(
        fontFamily = dmSansMedium,
        fontSize = 22.sp
    ),
    titleMedium = TextStyle(
        fontFamily = dmSansMedium,
        fontSize = 16.sp
    ),
    titleSmall = TextStyle(
        fontFamily = dmSansMedium,
        fontSize = 14.sp
    ),

    bodyLarge = TextStyle(
        fontFamily = dmSansRegular,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = dmSansRegular,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = dmSansRegular,
        fontSize = 12.sp
    ),

    labelLarge = TextStyle(
        fontFamily = dmSansMedium,
        fontSize = 14.sp
    ),
    labelMedium = TextStyle(
        fontFamily = dmSansMedium,
        fontSize = 12.sp
    ),
    labelSmall = TextStyle(
        fontFamily = dmSansRegular,
        fontSize = 10.sp
    )
)