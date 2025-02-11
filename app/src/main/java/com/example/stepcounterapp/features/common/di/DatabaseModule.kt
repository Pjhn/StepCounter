package com.example.stepcounterapp.features.common.di

import android.content.Context
import androidx.room.Room
import com.example.stepcounterapp.features.common.database.StepRecordDatabase
import com.example.stepcounterapp.features.common.database.dao.StepRecordDao
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
}