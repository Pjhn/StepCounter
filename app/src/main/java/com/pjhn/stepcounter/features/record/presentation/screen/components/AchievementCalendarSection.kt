package com.pjhn.stepcounter.features.record.presentation.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pjhn.stepcounter.features.record.presentation.screen.components.calendar.CalendarView
import com.pjhn.stepcounter.ui.theme.Paddings
import com.pjhn.stepcounter.ui.theme.Shapes
import java.time.LocalDate
import java.time.YearMonth

private val CATEGORY_SECTION_PADDING= Paddings.medium

@Composable
fun AchievementCalendarSection(
    recordsProgress: State<List<Pair<LocalDate, Float>>>,
    modifier: Modifier = Modifier,
    onClick: (LocalDate) -> Unit = {}
) {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }

    val datesForMonth = remember(currentMonth, recordsProgress.value) {
        buildMonthProgress(
            month = currentMonth,
            progressList = recordsProgress.value
        )
    }

    val headerDate = remember(currentMonth) { currentMonth.atDay(1) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                shape = Shapes.large,
                color = MaterialTheme.colorScheme.surface)
            .padding(CATEGORY_SECTION_PADDING)
            .padding(bottom = 8.dp)
    ){
        CalendarView(
            month = headerDate,
            date = datesForMonth,
            displayNext = true,
            displayPrev = true,
            onClickNext = { currentMonth = currentMonth.plusMonths(1) },
            onClickPrev = { currentMonth = currentMonth.minusMonths(1) },
            onClick = onClick ,
            modifier = modifier
        )
    }
}

fun buildMonthProgress(
    month: YearMonth,
    progressList: List<Pair<LocalDate, Float>>
): List<Pair<LocalDate, Float>> {
    val list = (1..month.lengthOfMonth()).map { day ->
        val date = month.atDay(day)
        date to (progressList.find { it.first == date }?.second ?: 0f)
    }
    return list
}


