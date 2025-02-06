package com.example.stepcounterapp.features.main.domain.usecase

import com.example.stepcounterapp.features.common.model.UserRecord
import com.example.stepcounterapp.features.repository.UserRecordRepository
import javax.inject.Inject

class GetUserRecordUseCase @Inject constructor(
    private val repository: UserRecordRepository
) : IGetUserRecordUseCase{
    override suspend fun invoke(): Result<UserRecord> = repository.getUserRecord()
}