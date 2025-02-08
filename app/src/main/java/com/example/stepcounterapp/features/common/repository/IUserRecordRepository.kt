package com.example.stepcounterapp.features.common.repository

import com.example.stepcounterapp.features.common.model.StepRecord

interface IUserRecordRepository {
    fun getUserRecord(): Result<StepRecord>
}