package com.pjhn.stepcounter.features.record.presentation.input

import com.pjhn.stepcounter.features.common.model.enums.Duration
import com.pjhn.stepcounter.features.record.domain.enums.RecordCategories

interface IRecordViewModelInput {
    fun goBackToMain()

    fun selectCategory(category: RecordCategories)

    fun selectDuration(duration: Duration)

    fun openPlayStore()
}