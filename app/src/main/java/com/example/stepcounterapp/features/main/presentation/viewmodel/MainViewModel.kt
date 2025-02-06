package com.example.stepcounterapp.features.main.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stepcounterapp.features.common.model.UserRecord
import com.example.stepcounterapp.features.main.domain.usecase.GetUserRecordUseCase
import com.example.stepcounterapp.features.main.presentation.input.IMainViewModelInput
import com.example.stepcounterapp.features.main.presentation.output.IMainViewModelOutput
import com.example.stepcounterapp.features.main.presentation.output.MainState
import com.example.stepcounterapp.features.main.presentation.output.MainUiEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserRecordUseCase: GetUserRecordUseCase
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

    private val _userRecord = MutableStateFlow<UserRecord>(
        UserRecord(
            stepCount = 0,
            distance = 0.0
        )
    )
    val userRecord: StateFlow<UserRecord>
        get() = _userRecord

    init {
        fetchMain()
    }

    private fun fetchMain() {
        viewModelScope.launch {
            _mainState.value = MainState.Loading

            val userRecord = getUserRecordUseCase()
            _mainState.value = userRecord.fold(
                onSuccess = { record ->
                    _userRecord.value = record
                    MainState.Main
                },
                onFailure = { error ->
                    MainState.Failed(reason = error.message ?: "")
                }
            )
        }
    }

    override fun startMeasurement() {
        viewModelScope.launch {
            _mainState.value = MainState.Measuring
            _mainUiEffect.emit(
                MainUiEffect.StartMeasurement
            )
        }
    }

    override fun pauseMeasurement() {
        viewModelScope.launch {
            _mainState.value = MainState.Main
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