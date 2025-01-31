package com.example.stepcounterapp.ui.theme

import androidx.compose.material3.Typography
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
    //큰 디스플레이 타이틀 (가장 큰 텍스트)
    displayLarge = TextStyle(
        fontFamily = notoSansBold,
        fontSize = 60.sp,
        lineHeight = 72.sp,
        letterSpacing = (-0.5).sp
    ),
    displayMedium = TextStyle(
        fontFamily = notoSansBold,
        fontSize = 48.sp,
        lineHeight = 60.sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontFamily = notoSansBold,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    ),

    //헤드라인 스타일 (제목에 사용)
    headlineLarge = TextStyle(
        fontFamily = notoSansBold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = notoSansBold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = notoSansBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),

    //타이틀 (중요한 제목)
    titleLarge = TextStyle(
        fontFamily = notoSansMedium,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = notoSansMedium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
        fontFamily = notoSansMedium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),

    //본문 (일반적인 텍스트)
    bodyLarge = TextStyle(
        fontFamily = notoSansRegular,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = notoSansRegular,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = notoSansRegular,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),

    //라벨 스타일 (버튼, 태그 등에 사용)
    labelLarge = TextStyle(
        fontFamily = notoSansMedium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = notoSansMedium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = notoSansRegular,
        fontSize = 10.sp,
        lineHeight = 12.sp,
        letterSpacing = 0.5.sp
    )
)
