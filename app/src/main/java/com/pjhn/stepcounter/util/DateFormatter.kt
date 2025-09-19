package com.pjhn.stepcounter.util

import java.time.format.DateTimeFormatter
import java.util.Locale

object DateFormatter {
    val iso: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    // 월 - 연도 형식 (예: Feb 2017)
    fun monthYear(): DateTimeFormatter {
        return DateTimeFormatter.ofPattern("MMM yyyy", Locale.ENGLISH)
    }

    // 일반 날짜 형식 (예: Feb 12, 2017)
    fun general(): DateTimeFormatter {
        return DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH)
    }
}