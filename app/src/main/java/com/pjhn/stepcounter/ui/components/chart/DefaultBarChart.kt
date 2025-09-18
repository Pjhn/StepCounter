package com.pjhn.stepcounter.ui.components.chart

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
import com.github.mikephil.charting.formatter.ValueFormatter

private val CHART_HEIGHT = 120.dp

@Composable
fun DefaultBarChart(
    modifier: Modifier = Modifier,
    xValues: List<String>,
    yValues: List<Float>
) {
    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .height(CHART_HEIGHT),
        factory = { context ->
            BarChart(context).apply {
                configureChart(xValues, yValues)
                data = createBarData(yValues)
                animateY(1000)
                invalidate()
            }
        },
        update = { chart ->
            chart.configureChart(xValues, yValues)
            chart.data = createBarData(yValues)
            chart.notifyDataSetChanged()
            chart.invalidate()
        }
    )
}

private fun BarChart.configureChart(xLabels: List<String>, yLabels: List<Float>) {
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

        val maxY = yLabels.max()
        val interval = when {
            maxY <= 0.1f -> 0.02f
            maxY <= 1f -> 0.2f
            maxY <= 10f -> 2f
            maxY <= 100f -> 20f
            maxY <= 1_000f -> 200f
            maxY <= 10_000f -> 2_000f
            maxY <= 100_000f -> 20_000f
            maxY <= 1_000_000f -> 200_000f
            else -> 500_000f
        }

        granularity = interval
        axisMinimum = 0f
        textColor = Color.parseColor("#99363636")
    }
    axisRight.isEnabled = false

    setPinchZoom(false)
    setScaleEnabled(false)
    setTouchEnabled(false)

    setDrawValueAboveBar(true)
}

private fun createBarData(yValues: List<Float>): BarData {
    val entries = yValues.mapIndexed { index, value ->
        BarEntry(index.toFloat(), value)
    }
    val dataSet = BarDataSet(entries, "").apply {
        valueFormatter = object : ValueFormatter() {
            override fun getBarLabel(barEntry: BarEntry?): String? {
                if (barEntry == null) return ""
                if (barEntry.y == 0f) return ""
                return if (barEntry.y % 1f == 0f) barEntry.y.toInt().toString()
                else String.format("%.2f", barEntry.y)
            }
        }

        valueTextSize = 10f
        valueTextColor = Color.parseColor("#363636")

        setGradientColor(0xFFEC8F3D.toInt(), 0xFFE44747.toInt())
        setDrawValues(true)
    }
    return BarData(dataSet).apply { barWidth = 0.6f }
}
