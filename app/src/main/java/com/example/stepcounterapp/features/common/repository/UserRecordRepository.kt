package com.example.stepcounterapp.features.common.repository

import com.example.stepcounterapp.features.common.model.StepRecord
import javax.inject.Inject

class UserRecordRepository @Inject constructor() : IUserRecordRepository {
    override fun getUserRecord(): Result<StepRecord> {
        return Result.success(
            StepRecord(
                stepCount = 10
            )
        )
    }
}