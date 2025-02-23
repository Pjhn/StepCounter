package com.example.stepcounterapp.ui.components.chart

import android.graphics.Color
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

private val CHART_HEIGHT = 120.dp

@Composable
fun DefaultBarChart(
    modifier: Modifier = Modifier,
    xLabels: List<String>,
    yValues: List<Float>
) {
    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .height(CHART_HEIGHT),
        factory = { context ->
            BarChart(context).apply {
                configureChart(xLabels)
                data = createBarData(yValues)
                animateY(1000)
                invalidate()
            }
        },
        update = { chart ->
            chart.configureChart(xLabels)
            chart.data = createBarData(yValues)
            chart.notifyDataSetChanged()
            chart.invalidate()
        }
    )
}

private fun BarChart.configureChart(xLabels: List<String>) {
    description = Description().apply { text = "" }

    legend.isEnabled = false

    setDrawGridBackground(false)

    xAxis.apply {
        position = XAxis.XAxisPosition.BOTTOM
        setDrawGridLines(false)
        setDrawAxisLine(false)
        granularity = 1f
        valueFormatter = IndexAxisValueFormatter(xLabels)
    }

    axisLeft.apply {
        setDrawGridLines(true)
        setGridColor(Color.parseColor("#80CCCCCC"))
        setGridLineWidth(0.5f)
        setDrawAxisLine(false)
        axisMinimum = 0f
        setLabelCount(4, true)
    }
    axisRight.isEnabled = false

    setPinchZoom(false)
    setScaleEnabled(false)
}

private fun createBarData(yValues: List<Float>): BarData {
    val entries = yValues.mapIndexed { index, value ->
        BarEntry(index.toFloat(), value)
    }
    val dataSet = BarDataSet(entries, "").apply {
        setGradientColor(0xFFEC8F3D.toInt(), 0xFFE44747.toInt())
        setDrawValues(false)
    }
    return BarData(dataSet).apply { barWidth = 0.6f }
}
