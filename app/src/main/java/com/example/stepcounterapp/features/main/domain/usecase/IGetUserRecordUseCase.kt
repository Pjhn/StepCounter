package com.example.stepcounterapp.features.main.domain.usecase

import com.example.stepcounterapp.features.common.model.StepRecord

interface IGetUserRecordUseCase {
    suspend operator fun invoke(): Result<StepRecord>
}