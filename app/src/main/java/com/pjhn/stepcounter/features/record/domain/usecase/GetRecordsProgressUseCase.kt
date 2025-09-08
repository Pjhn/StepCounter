package com.pjhn.stepcounter.features.record.domain.usecase

import com.pjhn.stepcounter.features.common.repository.IUserRecordRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GetRecordsProgressUseCase @Inject constructor(
    private val repository: IUserRecordRepository
) {
    operator fun invoke(): Flow<List<Pair<LocalDate, Float>>> = repository.userRecordsProgress
}