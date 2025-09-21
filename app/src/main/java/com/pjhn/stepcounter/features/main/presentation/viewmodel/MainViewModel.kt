package com.pjhn.stepcounter.features.main.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pjhn.stepcounter.features.common.model.StepRecord
import com.pjhn.stepcounter.features.common.repository.StepGoalRepository
import com.pjhn.stepcounter.features.common.repository.UserRecordRepository
import com.pjhn.stepcounter.features.main.presentation.input.IMainViewModelInput
import com.pjhn.stepcounter.features.main.presentation.output.IMainViewModelOutput
import com.pjhn.stepcounter.features.main.presentation.output.MainState
import com.pjhn.stepcounter.features.main.presentation.output.MainUiEffect
import com.pjhn.stepcounter.features.main.presentation.output.SensorState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRecordRepository: UserRecordRepository,
    private val stepGoalRepository: StepGoalRepository
) : ViewModel(), IMainViewModelInput, IMainViewModelOutput {

    val output: IMainViewModelOutput = this
    val input: IMainViewModelInput = this

    private val _mainState: MutableStateFlow<MainState> =
        MutableStateFlow(MainState.Loading)
    override val mainState: StateFlow<MainState>
        get() = _mainState

    private val _sensorState: MutableStateFlow<SensorState> =
        MutableStateFlow(SensorState.DelayLow)
    override val sensorState: StateFlow<SensorState>
        get() = _sensorState

    private val _openDialogState: MutableStateFlow<Boolean> =
        MutableStateFlow(false)
    val openDialogState: StateFlow<Boolean>
        get() = _openDialogState

    private val _mainUiEffect = MutableSharedFlow<MainUiEffect>()
    override val mainUiEffect: SharedFlow<MainUiEffect>
        get() = _mainUiEffect

    val stepRecord: StateFlow<StepRecord> = userRecordRepository.userRecord
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = StepRecord()
        )

    val stepGoal: StateFlow<Int> = stepGoalRepository.goal
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = StepGoalRepository.Keys.DEFAULT_GOAL
        )

    init {
        initializeTodayRecord()
    }

    fun initializeTodayRecord() {
        viewModelScope.launch {
            userRecordRepository.initializeTodayRecord()
        }
    }

    fun updateMainState(state: MainState) {
        viewModelScope.launch {
            _mainState.update { state }
        }
    }

    fun updateSensorState() {
        viewModelScope.launch {
            _sensorState.update { current ->
                when (current) {
                    SensorState.DelayHigh -> SensorState.DelayLow
                    SensorState.DelayLow -> SensorState.DelayHigh
                }
            }
        }
    }

    override fun startMeasurement() {
        viewModelScope.launch {
            _mainUiEffect.emit(
                MainUiEffect.StartMeasurement
            )
        }
    }

    override fun pauseMeasurement() {
        viewModelScope.launch {
            _mainUiEffect.emit(
                MainUiEffect.PauseMeasurement
            )
        }
    }

    override fun openRecord() {
        viewModelScope.launch {
            _mainUiEffect.emit(
                MainUiEffect.OpenRecord
            )
        }
    }

    override fun updateSensorDelay() {
        viewModelScope.launch {
            _mainUiEffect.emit(
                MainUiEffect.UpdateSensorDelay
            )
        }
    }

    override fun requestWidget() {
        viewModelScope.launch {
            _mainUiEffect.emit(
                MainUiEffect.RequestWidget
            )
        }
    }

    override fun updateStepGoal(stepGoal: Int) {
        viewModelScope.launch {
            stepGoalRepository.updateGoal(stepGoal)
            userRecordRepository.saveUserRecord(
                StepRecord(
                    stepCount = stepRecord.value.stepCount,
                )
            )
        }
    }

    override fun updateStepCount(stepCount: Int) {
        viewModelScope.launch {
            userRecordRepository.saveUserRecord(
                StepRecord(
                    stepCount = stepCount,
                )
            )
        }
    }

    override fun togglePermissionDialog(open: Boolean) {
        viewModelScope.launch {
            _openDialogState.value = open
        }
    }
}