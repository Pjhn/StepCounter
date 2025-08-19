package com.example.stepcounterapp.features.common.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StepGoalRepository @Inject constructor(
    private val datastore: DataStore<Preferences>
): IStepGoalRepository {
    override val goal: Flow<Int> = datastore.data.map { pref -> pref[Keys.GOAL] ?: Keys.DEFAULT_GOAL }

    override suspend fun updateGoal(goal: Int) {
        datastore.edit { pref -> pref[Keys.GOAL] = goal}
    }

    override suspend fun getGoal(): Int {
        return datastore.data.map { pref -> pref[Keys.GOAL] ?: Keys.DEFAULT_GOAL }.first()
    }

    private object Keys {
        val GOAL = intPreferencesKey("goal")
        const val DEFAULT_GOAL= 6000
    }
}