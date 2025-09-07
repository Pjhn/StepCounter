package com.pjhn.stepcounter.features.record.presentation.screen.components.calendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import java.util.Calendar
import java.util.Locale

private fun Int.getDayOfWeek(): String? = Calendar.getInstance().apply {
    set(Calendar.DAY_OF_WEEK, this@getDayOfWeek)
}.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US).let {
    it.take(1).uppercase()
}
@Composable
fun WeekdayCell(weekday: Int, modifier: Modifier = Modifier){
    val text = weekday.getDayOfWeek()
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .fillMaxSize()
    ){
        Text(
            text = text.orEmpty(),
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}