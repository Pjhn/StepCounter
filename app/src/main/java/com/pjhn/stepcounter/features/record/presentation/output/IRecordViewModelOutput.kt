package com.pjhn.stepcounter.features.record.presentation.output

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface IRecordViewModelOutput {
    val recordState: StateFlow<RecordState>
    val recordUiEffect: SharedFlow<RecordUiEffect>
}

sealed class RecordUiEffect {
    object Back : RecordUiEffect()

    object OpenPlayStore : RecordUiEffect()
}