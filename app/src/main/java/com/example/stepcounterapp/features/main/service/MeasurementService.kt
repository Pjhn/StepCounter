package com.example.stepcounterapp.features.main.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
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
import com.example.stepcounterapp.MainActivity
import com.example.stepcounterapp.R
import com.example.stepcounterapp.features.common.model.StepRecord
import com.example.stepcounterapp.features.common.repository.IUserRecordRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

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

        startForegroundService()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_STEP_COUNTER) {
            val totalSteps = event.values[0]
            if (initialStepCount < 0) {
                initialStepCount = totalSteps
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
            .setContentText("걸음을 측정 중입니다")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        startForeground(1, notification)
    }

    companion object {
        const val TAG = "MeasurementService"
        const val CHANNEL_ID = "measurement_channel"
        const val CHANNEL_NAME = "measurement Service"
    }
}