package com.pjhn.stepcounter.features.record.domain.usecase

import com.pjhn.stepcounter.features.common.model.StepRecord
import com.pjhn.stepcounter.features.common.repository.UserRecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStepRecordUseCase @Inject constructor(
    private val userRecordRepository: UserRecordRepository
) {
    operator fun invoke() : Flow<StepRecord> = userRecordRepository.userRecord
}