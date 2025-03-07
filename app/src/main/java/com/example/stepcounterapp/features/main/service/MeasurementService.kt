package com.example.stepcounterapp.features.main.service

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
import androidx.glance.appwidget.updateAll
import com.example.stepcounterapp.MainActivity
import com.example.stepcounterapp.R
import com.example.stepcounterapp.features.common.model.StepRecord
import com.example.stepcounterapp.features.common.repository.IUserRecordRepository
import com.example.stepcounterapp.features.widget.StepCountWidget
import com.example.stepcounterapp.features.widget.StepCountWidgetReceiver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val WIDGET_UPDATE_DELAY: Long = 6000

@AndroidEntryPoint
class MeasurementService : Service(), SensorEventListener {
    @Inject
    lateinit var sensorManager: SensorManager

    @Inject
    lateinit var userRecordRepository: IUserRecordRepository

    private var stepCounterSensor: Sensor? = null

    private var initialStepCount: Float = -1f

    override fun onCreate() {
        super.onCreate()
        isServiceRunning = true

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

        pinWidget()
        startForegroundService()

        CoroutineScope(Dispatchers.IO).launch {
            while (isServiceRunning) {
                StepCountWidget.updateAll(this@MeasurementService)
                delay(WIDGET_UPDATE_DELAY)
            }
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_STEP_COUNTER) {
            val totalSteps = event.values[0]
            if (initialStepCount < 0) {
                initialStepCount = totalSteps
                return
            }
            val stepsSinceStart = totalSteps - initialStepCount
            Log.d(
                TAG, "걸음 수: $stepsSinceStart, " +
                        "totalSteps: $totalSteps, initialStepCount: $initialStepCount"
            )

            CoroutineScope(Dispatchers.IO).launch {
                val record = StepRecord(
                    stepCount = stepsSinceStart.toInt()
                )
                userRecordRepository.saveUserRecord(record)
            }
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