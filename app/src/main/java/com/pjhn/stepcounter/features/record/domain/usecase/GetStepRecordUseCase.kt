package com.pjhn.stepcounter.features.record.domain.usecase

import com.pjhn.stepcounter.features.common.repository.UserRecordRepository
import javax.inject.Inject

class GetStepRecordUseCase @Inject constructor(
    private val userRecordRepository: UserRecordRepository
) {
    operator fun invoke() = userRecordRepository.userRecord
}