package com.example.stepcounterapp.features.main

import android.Manifest
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.stepcounterapp.features.main.presentation.output.MainState
import com.example.stepcounterapp.features.main.presentation.output.MainUiEffect
import com.example.stepcounterapp.features.main.presentation.output.SensorState
import com.example.stepcounterapp.features.main.presentation.screen.MainScreen
import com.example.stepcounterapp.features.main.presentation.viewmodel.MainViewModel
import com.example.stepcounterapp.features.main.service.MeasurementService
import com.example.stepcounterapp.features.widget.StepCountWidgetReceiver
import com.example.stepcounterapp.ui.navigation.safeNavigate
import com.example.stepcounterapp.ui.theme.StepCounterAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        observeUiEffects()
        requestPermissions()

        if (MeasurementService.isServiceRunning) {
            viewModel.updateMainState(MainState.Measuring)
        }

        return ComposeView(requireContext()).apply {
            setContent {
                StepCounterAppTheme {
                    MainScreen(
                        mainStateHolder = viewModel.mainState.collectAsState(),
                        sensorStateHolder = viewModel.sensorState.collectAsState(),
                        input = viewModel.input,
                        stepRecord = viewModel.stepRecord.collectAsState(),
                        stepGoal = viewModel.stepGoal.collectAsState()
                    )
                }
            }
        }
    }

    private fun observeUiEffects() {
        val navController = findNavController()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.output.mainUiEffect.collectLatest {
                    when (it) {
                        is MainUiEffect.StartMeasurement -> {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                requireContext().startForegroundService(sensorIntent())
                            } else {
                                requireContext().startService(sensorIntent())
                            }
                        }

                        is MainUiEffect.PauseMeasurement -> {
                            requireContext().stopService(sensorIntent())
                        }

                        is MainUiEffect.OpenRecord -> {
                            navController.safeNavigate(
                                MainFragmentDirections.actionMainToRecord()
                            )
                        }

                        is MainUiEffect.UpdateSensorDelay -> {
                            updateSensorState()

                            if(MeasurementService.isServiceRunning){
                                requireContext().startService(sensorIntent())
                            }
                        }

                        is MainUiEffect.RequestWidget -> {
                            requestWidget()
                        }
                    }
                }
            }
        }
    }

    private fun requestPermissions() {
        val permissionsToRequest = mutableListOf<String>()

        // ACTIVITY_RECOGNITION 권한 (Android 10 이상)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACTIVITY_RECOGNITION
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsToRequest.add(Manifest.permission.ACTIVITY_RECOGNITION)
            }
        }

        // POST_NOTIFICATIONS 권한 (Android 13 이상)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsToRequest.add(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        if (permissionsToRequest.isNotEmpty()) {
            permissionLauncher.launch(permissionsToRequest.toTypedArray())
        }
    }

    private fun updateSensorState(){
        viewModel.updateSensorState()
    }

    private fun sensorIntent(): Intent{
        return Intent(requireContext(), MeasurementService::class.java).apply {
            action = when (viewModel.sensorState.value){
                SensorState.DelayLow -> MeasurementService.SENSOR_DELAY_LOW
                SensorState.DelayHigh -> MeasurementService.SENSOR_DELAY_HIGH
            }
        }
    }

    private fun requestWidget() {
        val ctx = requireContext()
        val widgetManager = AppWidgetManager.getInstance(ctx)
        val widgetComponent = ComponentName(ctx, StepCountWidgetReceiver::class.java)
        val widgetIds = widgetManager.getAppWidgetIds(widgetComponent)

        if (widgetIds.isEmpty()) {
            widgetManager.requestPinAppWidget(widgetComponent, null, null)
        }
    }
}