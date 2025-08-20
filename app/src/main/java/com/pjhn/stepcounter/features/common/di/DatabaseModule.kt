package com.pjhn.stepcounter.features.common.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.pjhn.stepcounter.features.common.database.StepRecordDatabase
import com.pjhn.stepcounter.features.common.database.dao.StepRecordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideStepRecordDatabase(
        @ApplicationContext context: Context
    ): StepRecordDatabase {
        return Room.databaseBuilder(
            context,
            StepRecordDatabase::class.java,
            "step_record_db"
        ).build()
    }

    @Provides
    fun provideStepRecordDao(database: StepRecordDatabase): StepRecordDao {
        return database.stepRecordDao()
    }

    @Provides
    @Singleton
    fun providePreferencesDatastore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile("user_prefs")
            }
    )
}