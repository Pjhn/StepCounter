package com.example.stepcounterapp.features.main.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.stepcounterapp.features.common.model.UserRecord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    private val _userRecord = MutableStateFlow<UserRecord>(
        UserRecord(
            stepCount = 4028,
            distance = 3210.0
        )
    )
    val userRecord: StateFlow<UserRecord>
        get() = _userRecord
}