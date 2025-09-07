package com.pjhn.stepcounter.features.record.presentation.screen.components.calendar

import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import java.time.LocalDate

@Composable
fun CalendarGrid(
    date: List<Pair<LocalDate, Boolean>>,
    onClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
){
    val weekFirstDay = date.first().first.dayOfWeek.value
    val weekdays = getWeekDays()
    CalendarCustomLayout(
        modifier = modifier
    ){
        weekdays.forEach { weekday ->
            WeekdayCell(weekday = weekday)
        }
        repeat(if(weekFirstDay != 7) weekFirstDay else 0 ) {
            Spacer(modifier = Modifier)
        }
        date.forEach {
            CalendarCell(
                date = it.first, isMarked = it.second, onClick = { onClick(it.first) },
                modifier = Modifier
            )
        }
    }

}

fun getWeekDays(): List<Int> {
    val weekdays = (1..7).toList()
    return weekdays
}