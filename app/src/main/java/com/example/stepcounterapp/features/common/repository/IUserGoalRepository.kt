package com.example.stepcounterapp.features.common.repository

import kotlinx.coroutines.flow.Flow

interface IUserGoalRepository {
    val goal: Flow<Int>
    suspend fun updateGoal(goal: Int)
    suspend fun getGoal(): Int
}