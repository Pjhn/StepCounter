package com.example.stepcounterapp.features.main.domain.usecase

import com.example.stepcounterapp.features.common.model.StepRecord
import com.example.stepcounterapp.features.common.repository.UserRecordRepository
import javax.inject.Inject

class GetUserRecordUseCase @Inject constructor(
    private val repository: UserRecordRepository
) : IGetUserRecordUseCase{
    override suspend fun invoke(): Result<StepRecord> = repository.getUserRecord()
}