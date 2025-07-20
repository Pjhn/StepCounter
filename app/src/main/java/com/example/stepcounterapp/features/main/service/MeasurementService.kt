package com.example.stepcounterapp.features.main.service

import StepCountWidget
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.datastore.preferences.core.MutablePreferences
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import com.example.stepcounterapp.MainActivity
import com.example.stepcounterapp.R
import com.example.stepcounterapp.features.common.model.StepRecord
import com.example.stepcounterapp.features.common.repository.IUserRecordRepository
import com.example.stepcounterapp.features.widget.StepCountWidgetReceiver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@AndroidEntryPoint
class MeasurementService : Service(), SensorEventListener {
    @Inject
    lateinit var sensorManager: SensorManager

    @Inject
    lateinit var userRecordRepository: IUserRecordRepository

    private var stepCounterSensor: Sensor? = null

    private var prevStepCount: Float = -1f

    private val stepFlow = MutableStateFlow(0)

    override fun onCreate() {
        super.onCreate()
        isServiceRunning = true

        CoroutineScope(Dispatchers.IO).launch {
            userRecordRepository.userRecord.firstOrNull()?.let {
                stepFlow.value = it.stepCount ?: 0
            }

            stepFlow
                .sample(1000)
                .collect{steps ->
                    userRecordRepository.saveUserRecord(StepRecord(stepCount = steps))
                    updateWidget(this@MeasurementService, steps)
                }
        }

        registerSensor()
        pinWidget()
        startForegroundService()
    }

    private fun registerSensor(){
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (stepCounterSensor != null) {
            sensorManager.registerListener(
                this,
                stepCounterSensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        } else {
            Log.e(TAG, "센서를 찾을 수 없습니다.")
        }
    }
    private suspend fun updateWidget(context: Context, steps: Int){
        val manager = GlanceAppWidgetManager(context)
        val ids = manager.getGlanceIds(StepCountWidget::class.java)

        ids.forEach { id ->
            updateAppWidgetState(
                context = context,
                glanceId = id
            ){ prefs: MutablePreferences ->
                prefs[StepCountWidget.TODAY_STEPS] = steps
            }
        }
        StepCountWidget.updateAll(this@MeasurementService)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_STEP_COUNTER) {
            val totalSteps = event.values[0]
            if (prevStepCount < 0) {
                prevStepCount = totalSteps
                return
            }
            val stepsSinceStart = (totalSteps - prevStepCount).toInt()
            Log.d(
                TAG, "걸음 수: $stepsSinceStart, " +
                        "totalSteps: $totalSteps, prevStepCount: $prevStepCount"
            )

            stepFlow.value += stepsSinceStart
            prevStepCount = totalSteps
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        sensorManager.unregisterListener(this)
        isServiceRunning = false
        super.onDestroy()
    }

    private fun startForegroundService() {
        val channelId = CHANNEL_ID
        val channelName = CHANNEL_NAME

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as
                    NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_IMMUTABLE else 0
        )

        val notification: Notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_time)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(getString(R.string.notification_body))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        startForeground(1, notification)
    }

    private fun pinWidget() {
        val widgetManager = AppWidgetManager.getInstance(this)
        val widgetComponent = ComponentName(this, StepCountWidgetReceiver::class.java)
        val widgetIds = widgetManager.getAppWidgetIds(widgetComponent)

        if (widgetIds.isNotEmpty()) {
            return
        }

        widgetManager.requestPinAppWidget(widgetComponent, null, null)
    }


    companion object {
        const val TAG = "MeasurementService"
        const val CHANNEL_ID = "measurement_channel"
        const val CHANNEL_NAME = "measurement Service"
        var isServiceRunning = false
    }
}