package com.example.stepcounterapp.features.common.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserGoalRepository @Inject constructor(
    private val datastore: DataStore<Preferences>
): IUserGoalRepository {
    override val goal: Flow<Int> = datastore.data.map { pref -> pref[keys.GOAL] ?: keys.DEFAULT_GOAL }

    override suspend fun updateGoal(goal: Int) {
        datastore.edit { pref -> pref[keys.GOAL] = goal}
    }

    override suspend fun getGoal(): Int {
        return datastore.data.map { pref -> pref[keys.GOAL] ?: keys.DEFAULT_GOAL }.first()
    }

    private object keys {
        val GOAL = intPreferencesKey("goal")
        const val DEFAULT_GOAL= 6000
    }
}