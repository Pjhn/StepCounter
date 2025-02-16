package com.example.stepcounterapp.features.record.presentation.input

import com.example.stepcounterapp.features.common.model.enums.Duration
import com.example.stepcounterapp.features.record.domain.enums.RecordCategories

interface IRecordViewModelInput {
    fun goBackToMain()

    fun selectCategory(category: RecordCategories)

    fun selectDuration(duration: Duration)
}