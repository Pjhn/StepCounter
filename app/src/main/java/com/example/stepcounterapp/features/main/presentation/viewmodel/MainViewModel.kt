package com.example.stepcounterapp.features.main.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stepcounterapp.features.common.model.StepRecord
import com.example.stepcounterapp.features.common.repository.UserRecordRepository
import com.example.stepcounterapp.features.main.presentation.input.IMainViewModelInput
import com.example.stepcounterapp.features.main.presentation.output.IMainViewModelOutput
import com.example.stepcounterapp.features.main.presentation.output.MainState
import com.example.stepcounterapp.features.main.presentation.output.MainUiEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRecordRepository: UserRecordRepository
) : ViewModel(), IMainViewModelInput, IMainViewModelOutput {

    val output: IMainViewModelOutput = this
    val input: IMainViewModelInput = this

    private val _mainState: MutableStateFlow<MainState> =
        MutableStateFlow(MainState.Loading)
    override val mainState: StateFlow<MainState>
        get() = _mainState

    private val _mainUiEffect = MutableSharedFlow<MainUiEffect>()
    override val mainUiEffect: SharedFlow<MainUiEffect>
        get() = _mainUiEffect

    val stepRecord: StateFlow<StepRecord> = userRecordRepository.userRecord
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = StepRecord()
        )

    init {
        viewModelScope.launch {
            userRecordRepository.initializeTodayRecord()
        }
    }

    fun updateMainState(state: MainState) {
        viewModelScope.launch {
            _mainState.value = state
        }
    }

    override fun startMeasurement() {
        viewModelScope.launch {
            updateMainState(MainState.Measuring)
            _mainUiEffect.emit(
                MainUiEffect.StartMeasurement
            )
        }
    }

    override fun pauseMeasurement() {
        viewModelScope.launch {
            updateMainState(MainState.Main)
            _mainUiEffect.emit(
                MainUiEffect.PauseMeasurement
            )
        }
    }

    override fun openSensitivityDialog() {
        viewModelScope.launch {
            _mainUiEffect.emit(
                MainUiEffect.OpenSensitivityDialog
            )
        }
    }
}