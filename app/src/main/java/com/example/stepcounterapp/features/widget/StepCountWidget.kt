package com.example.stepcounterapp.features.widget

import android.content.Context
import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Column
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.example.stepcounterapp.features.common.repository.IUserRecordRepository
import com.example.stepcounterapp.ui.theme.colors
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.first

object StepCountWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val entryPoint = EntryPointAccessors.fromApplication(
            context.applicationContext,
            WidgetRepositoryEntryPoint::class.java
        )
        val repository: IUserRecordRepository = entryPoint.getUserRecordRepository()
        val stepCount = repository.userRecord.first().stepCount ?: 0

        provideContent {
            Content(stepCount)
        }
    }

    @Composable
    private fun Content(stepCount: Int){
        Column(
            modifier = GlanceModifier.padding(16.dp)
                .background(MaterialTheme.colors.background),
        ) {
            Text(text = "Steps: $stepCount", style = TextStyle(fontSize = 18.sp))
        }
    }
}