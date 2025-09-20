package com.pjhn.stepcounter.features.main

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.pjhn.stepcounter.features.main.presentation.output.MainState
import com.pjhn.stepcounter.features.main.presentation.output.MainUiEffect
import com.pjhn.stepcounter.features.main.presentation.output.SensorState
import com.pjhn.stepcounter.features.main.presentation.screen.MainScreen
import com.pjhn.stepcounter.features.main.presentation.viewmodel.MainViewModel
import com.pjhn.stepcounter.features.main.service.MeasurementService
import com.pjhn.stepcounter.features.widget.StepCountWidgetReceiver
import com.pjhn.stepcounter.ui.navigation.safeNavigate
import com.pjhn.stepcounter.ui.theme.StepCounterAppTheme
import com.pjhn.stepcounter.util.PermissionUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()

    val permissionLauncher = registerForActivityResult(
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
                        input = viewModel.input,
                        mainStateHolder = viewModel.mainState.collectAsState(),
                        sensorStateHolder = viewModel.sensorState.collectAsState(),
                        stepRecord = viewModel.stepRecord.collectAsState(),
                        stepGoal = viewModel.stepGoal.collectAsState(),
                        openPermissionDialog = viewModel.openDialogState.collectAsState()
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.initializeTodayRecord()
    }

    private fun observeUiEffects() {
        val navController = findNavController()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.output.mainUiEffect.collectLatest {
                    when (it) {
                        is MainUiEffect.StartMeasurement -> {
                            if (PermissionUtils.hasPermissions(requireContext())) {
                                updateMainState(MainState.Measuring)
                                requireContext().startForegroundService(sensorIntent())

                            } else if (PermissionUtils.hasDeniedPermission(requireActivity())) {
                                togglePermissionDialog(true)
                            } else {
                                PermissionUtils.requestPermissions(permissionLauncher)
                            }
                        }

                        is MainUiEffect.PauseMeasurement -> {
                            updateMainState(MainState.Main)
                            requireContext().stopService(sensorIntent())
                        }

                        is MainUiEffect.OpenRecord -> {
                            navController.safeNavigate(
                                MainFragmentDirections.actionMainToRecord()
                            )
                        }

                        is MainUiEffect.UpdateSensorDelay -> {
                            updateSensorState()

                            if (MeasurementService.isServiceRunning) {
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
        if (PermissionUtils.hasPermissions(requireContext())) return
        PermissionUtils.requestPermissions(permissionLauncher)
    }

    private fun updateSensorState() {
        viewModel.updateSensorState()
    }

    private fun updateMainState(state: MainState) {
        viewModel.updateMainState(state)
    }

    private fun togglePermissionDialog(open: Boolean){
        viewModel.togglePermissionDialog(open)
    }

    private fun sensorIntent(): Intent {
        return Intent(requireContext(), MeasurementService::class.java).apply {
            action = when (viewModel.sensorState.value) {
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