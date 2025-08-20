package com.pjhn.stepcounter.features.record.domain.usecase

import com.pjhn.stepcounter.features.common.model.StepRecord
import com.pjhn.stepcounter.features.common.repository.IUserRecordRepository
import java.time.LocalDate
import javax.inject.Inject

class GetRecordsForPeriodUseCase @Inject constructor(
    private val repository: IUserRecordRepository
) {
    suspend operator fun invoke(startDate: LocalDate, endDate: LocalDate): List<StepRecord> {
        return repository.getRecordsForPeriod(startDate, endDate)
    }
}