package com.pjhn.stepcounter.util

object TimeFormatter {
    fun formatTime(totalSeconds: Long): String {
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        return "${hours}h ${minutes}m ${seconds}s"
    }
}