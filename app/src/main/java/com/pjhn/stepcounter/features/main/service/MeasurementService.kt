package com.pjhn.stepcounter.features.main.service

import StepCountWidget
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
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.datastore.preferences.core.MutablePreferences
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import com.pjhn.stepcounter.MainActivity
import com.pjhn.stepcounter.R
import com.pjhn.stepcounter.features.common.model.StepRecord
import com.pjhn.stepcounter.features.common.repository.IUserRecordRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@OptIn(FlowPreview::class)
@AndroidEntryPoint
class MeasurementService : Service(), SensorEventListener {
    @Inject
    lateinit var sensorManager: SensorManager

    @Inject
    lateinit var userRecordRepository: IUserRecordRepository

    private lateinit var notificationManager: NotificationManager

    private var stepCounterSensor: Sensor? = null

    private var prevStepCount: Float = -1f

    private val stepFlow = MutableStateFlow(0)

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            SENSOR_DELAY_HIGH -> applySensorDelay(SensorManager.SENSOR_DELAY_NORMAL)
            SENSOR_DELAY_LOW -> applySensorDelay(SensorManager.SENSOR_DELAY_UI)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun applySensorDelay(state: Int) {
        sensorManager.unregisterListener(this)
        stepCounterSensor?.let {
            sensorManager.registerListener(this, it, state)
        } ?: registerSensor(state)
    }

    override fun onCreate() {
        super.onCreate()
        isServiceRunning = true

        serviceScope.launch {
            if (userRecordRepository.isNewDay()) userRecordRepository.initializeTodayRecord()

            userRecordRepository.userRecord.firstOrNull()?.let {
                stepFlow.value = it.stepCount ?: 0
            }

            stepFlow
                .collect { steps ->
                    userRecordRepository.saveUserRecord(StepRecord(stepCount = steps))
                    updateWidget(this@MeasurementService, steps)
                    updateNotification(steps)
                }
        }

        startForegroundService()
    }

    private fun registerSensor(state: Int) {
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (stepCounterSensor != null) {
            sensorManager.registerListener(
                this,
                stepCounterSensor,
                state
            )
        } else {
            Log.e(TAG, "센서를 찾을 수 없습니다.")
        }
    }

    private suspend fun updateWidget(context: Context, steps: Int) {
        val manager = GlanceAppWidgetManager(context)
        val ids = manager.getGlanceIds(StepCountWidget::class.java)

        ids.forEach { id ->
            updateAppWidgetState(
                context = context,
                glanceId = id
            ) { prefs: MutablePreferences ->
                prefs[StepCountWidget.TODAY_STEPS] = steps
            }
        }
        StepCountWidget.updateAll(this@MeasurementService)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type != Sensor.TYPE_STEP_COUNTER) return
        val totalSteps = event.values[0]

        serviceScope.launch {
            if (userRecordRepository.isNewDay()) {
                stepFlow.value = 0  // 오늘 기록 초기화
                prevStepCount = -1f // 센서 기준점 초기화
                userRecordRepository.saveUserRecord(StepRecord(stepCount = 0))
            }

            if (prevStepCount < 0) {
                prevStepCount = totalSteps
                return@launch
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
        serviceScope.cancel()
        isServiceRunning = false
        super.onDestroy()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        runBlocking {
            userRecordRepository.saveUserRecord(StepRecord(stepCount = stepFlow.value))
        }
        super.onTaskRemoved(rootIntent)
    }


    private fun startForegroundService() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager
        notificationManager.createNotificationChannel(channel)

        startForeground(1, buildNotification(stepFlow.value))
    }

    private fun buildNotification(steps: Int): Notification {
        val content = RemoteViews(packageName, R.layout.notification_steps).apply {
            setTextViewText(R.id.tv_step_count, "$steps")
        }
        val notificationIntent = Intent(this, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_IMMUTABLE else 0
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_time)
            .setCustomContentView(content)
            .setOngoing(true) // 스와이프 삭제 방지
            .setOnlyAlertOnce(true) // 값 업데이트 시 재알림 소리 x
            .setSilent(true) // 진동, 소리 x
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .build()
    }

    private fun updateNotification(steps: Int) {
        notificationManager.notify(1, buildNotification(steps))
    }

    companion object {
        const val TAG = "MeasurementService"
        const val CHANNEL_ID = "measurement_channel"
        const val CHANNEL_NAME = "measurement Service"
        const val SENSOR_DELAY_LOW = "SENSOR_DELAY_LOW"
        const val SENSOR_DELAY_HIGH = "SENSOR_DELAY_HIGH"
        var isServiceRunning = false
    }
}