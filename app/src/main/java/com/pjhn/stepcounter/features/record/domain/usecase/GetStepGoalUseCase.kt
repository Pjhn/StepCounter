package com.pjhn.stepcounter.features.record.domain.usecase

import com.pjhn.stepcounter.features.common.repository.IStepGoalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStepGoalUseCase @Inject constructor(
    private val stepGoalRepository: IStepGoalRepository
) {
    operator fun invoke(): Flow<Int> = stepGoalRepository.goal
}