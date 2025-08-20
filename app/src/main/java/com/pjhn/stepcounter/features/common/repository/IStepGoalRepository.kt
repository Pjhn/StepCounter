package com.pjhn.stepcounter.features.common.repository

import kotlinx.coroutines.flow.Flow

interface IStepGoalRepository {
    val goal: Flow<Int>
    suspend fun updateGoal(goal: Int)
    suspend fun getGoal(): Int
}