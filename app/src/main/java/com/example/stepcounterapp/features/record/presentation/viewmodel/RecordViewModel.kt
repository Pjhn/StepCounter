package com.example.stepcounterapp.features.record.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stepcounterapp.features.common.model.StepRecord
import com.example.stepcounterapp.features.common.model.enums.Duration
import com.example.stepcounterapp.features.common.model.enums.Duration.*
import com.example.stepcounterapp.features.common.repository.StepGoalRepository
import com.example.stepcounterapp.features.common.repository.UserRecordRepository
import com.example.stepcounterapp.features.record.domain.enums.RecordCategories
import com.example.stepcounterapp.features.record.domain.enums.RecordCategories.*
import com.example.stepcounterapp.features.record.domain.usecase.GetRecordsForPeriodUseCase
import com.example.stepcounterapp.features.record.presentation.input.IRecordViewModelInput
import com.example.stepcounterapp.features.record.presentation.output.IRecordViewModelOutput
import com.example.stepcounterapp.features.record.presentation.output.RecordState
import com.example.stepcounterapp.features.record.presentation.output.RecordUiEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val getRecordsForPeriodUseCase: GetRecordsForPeriodUseCase,
    private val userRecordRepository: UserRecordRepository,
    private val stepGoalRepository: StepGoalRepository
) : ViewModel(), IRecordViewModelInput, IRecordViewModelOutput {

    val output: IRecordViewModelOutput = this
    val input: IRecordViewModelInput = this

    private val _recordState: MutableStateFlow<RecordState> =
        MutableStateFlow(RecordState.Main)
    override val recordState: StateFlow<RecordState>
        get() = _recordState

    private val _recordUiEffect = MutableSharedFlow<RecordUiEffect>()
    override val recordUiEffect: SharedFlow<RecordUiEffect>
        get() = _recordUiEffect

    private val _selectedCategory = MutableStateFlow<RecordCategories>(STEP)
    val selectedCategory: StateFlow<RecordCategories>
        get() = _selectedCategory

    private val _selectedDuration = MutableStateFlow<Duration>(WEEK)
    val selectedDuration: StateFlow<Duration>
        get() = _selectedDuration

    private val _chartRecords = MutableStateFlow<List<StepRecord>>(emptyList())
    val chartRecords: StateFlow<List<StepRecord>> = _chartRecords

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
        viewModelScope.launch {
            val today = LocalDate.now()
            val oneYearAgo = today.minusYears(1)
            _chartRecords.value = getRecordsForPeriodUseCase(oneYearAgo, today)
        }
    }

    override fun goBackToMain() {
        viewModelScope.launch {
            _recordUiEffect.emit(
                RecordUiEffect.Back
            )
        }
    }

    override fun selectCategory(category: RecordCategories) {
        viewModelScope.launch {
            _selectedCategory.value = category
        }
    }

    override fun selectDuration(duration: Duration) {
        viewModelScope.launch {
            _selectedDuration.value = duration
        }
    }
}