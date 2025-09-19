package com.pjhn.stepcounter.ui.components.chart

import android.graphics.Color
import android.icu.text.DecimalFormat
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.LimitLine
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
    yValues: List<Float>,
    showAvg: Boolean = false,
    showDur: Boolean = true
) {
    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .height(CHART_HEIGHT),
        factory = { context ->
            BarChart(context).apply {
                configureChart(xValues, yValues, showAvg, showDur)
                data = createBarData(yValues)
                animateY(1000)
                invalidate()
            }
        },
        update = { chart ->
            chart.configureChart(xValues, yValues, showAvg, showDur)
            chart.data = createBarData(yValues)
            chart.notifyDataSetChanged()
            chart.invalidate()
        }
    )
}

private fun BarChart.configureChart(xLabels: List<String>, yLabels: List<Float>, showAvg: Boolean, showDur: Boolean) {
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
        setSpaceTop(17f)
        removeAllLimitLines()

        if(showDur){
            val durationAverage = yLabels.average().toFloat()
            val durationAverageLimitLine = LimitLine(durationAverage, "Duration Avg ${numberFormatter(durationAverage)}").apply {
                lineWidth = 1.5f
                enableDashedLine(10f, 10f, 0f)
                textSize = 9f
                textColor = Color.parseColor("#99363636")
                lineColor = Color.parseColor("#99363636")
                labelPosition = LimitLine.LimitLabelPosition.LEFT_TOP
                yOffset = 6f
            }
            addLimitLine(durationAverageLimitLine)
        }

        if(showAvg){
            val average = yLabels.filter { it > 0f }.average().toFloat()
            val averageLimitLine = LimitLine(average, "Avg ${numberFormatter(average)}").apply {
                lineWidth = 1.5f
                enableDashedLine(10f, 10f, 0f)
                textSize = 9f
                textColor = Color.parseColor("#99EC8F3D")
                lineColor = Color.parseColor("#99EC8F3D")
                labelPosition = LimitLine.LimitLabelPosition.LEFT_TOP
                yOffset = 6f
            }
            addLimitLine(averageLimitLine)
        }

        val maxY = yLabels.max()
        val interval = when {
            maxY <= 0f -> 50f
            maxY <= 10f -> 2f
            maxY <= 50f -> 10f
            maxY <= 100f -> 20f
            maxY <= 200f -> 20f
            maxY <= 500f -> 50f
            maxY <= 1_000f -> 100f
            maxY <= 5_000f -> 500f
            maxY <= 10_000f -> 1_000f
            maxY <= 100_000f -> 10_000f
            else -> 50_000f
        }

        granularity = interval
        axisMinimum = 0f
        textColor = Color.parseColor("#99363636")
        valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String? {
                return numberFormatter(value)
            }
        }
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
                return numberFormatter(barEntry.y)
            }
        }

        valueTextSize = 10f
        valueTextColor = Color.parseColor("#363636")

        setGradientColor(0xFFEC8F3D.toInt(), 0xFFE44747.toInt())
        setDrawValues(true)
    }
    return BarData(dataSet).apply { barWidth = 0.6f }
}

private fun numberFormatter(value: Float): String {
    val (number, unit) = when {
        value >= 1_000_000_000f -> value / 1_000_000_000f to "B"
        value >= 1_000_000f -> value / 1_000_000f to "M"
        value >= 1_000f -> value / 1_000f to "K"
        else -> return if (value % 1f == 0f) value.toInt().toString() else
            DecimalFormat("#.##").format(value)
    }

    return DecimalFormat("#.##").format(number) + unit
}
