package com.example.stepcounterapp.features.common.repository

import com.example.stepcounterapp.features.common.model.UserRecord
import javax.inject.Inject

class UserRecordRepository @Inject constructor() : IUserRecordRepository {
    override fun getUserRecord(): Result<UserRecord> {
        return Result.success(
            UserRecord(
                stepCount = 10
            )
        )
    }
}