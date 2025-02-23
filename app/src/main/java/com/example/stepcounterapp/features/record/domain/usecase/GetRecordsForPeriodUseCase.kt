package com.example.stepcounterapp.features.record.domain.usecase

import com.example.stepcounterapp.features.common.model.StepRecord
import com.example.stepcounterapp.features.common.repository.IUserRecordRepository
import java.time.LocalDate
import javax.inject.Inject

class GetRecordsForPeriodUseCase @Inject constructor(
    private val repository: IUserRecordRepository
) {
    suspend operator fun invoke(startDate: LocalDate, endDate: LocalDate): List<StepRecord> {
        return repository.getRecordsForPeriod(startDate, endDate)
    }
}