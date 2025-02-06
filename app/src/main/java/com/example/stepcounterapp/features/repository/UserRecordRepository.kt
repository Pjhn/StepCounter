package com.example.stepcounterapp.features.repository

import com.example.stepcounterapp.features.common.model.UserRecord

class UserRecordRepository {
    fun getUserRecord(): UserRecord {
        return UserRecord(
            stepCount = 10
        )
    }
}