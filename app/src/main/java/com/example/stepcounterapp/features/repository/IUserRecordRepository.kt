package com.example.stepcounterapp.features.repository

import com.example.stepcounterapp.features.common.model.UserRecord

interface IUserRecordRepository {
    fun getUserRecord(): Result<UserRecord>
}