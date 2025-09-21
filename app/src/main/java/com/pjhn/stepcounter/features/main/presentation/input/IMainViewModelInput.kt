package com.pjhn.stepcounter.features.main.presentation.input

interface IMainViewModelInput {
    fun openRecord()

    fun startMeasurement()

    fun pauseMeasurement()

    fun updateSensorDelay()

    fun requestWidget()

    fun updateStepGoal(stepGoal: Int)

    fun updateStepCount(stepCount: Int)

    fun togglePermissionDialog(open: Boolean)
}