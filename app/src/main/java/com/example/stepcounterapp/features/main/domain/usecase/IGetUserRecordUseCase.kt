package com.example.stepcounterapp.features.main.domain.usecase

import com.example.stepcounterapp.features.common.model.UserRecord

interface IGetUserRecordUseCase {
    suspend operator fun invoke(): Result<UserRecord>
}