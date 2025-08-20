package com.pjhn.stepcounter.features.common.di

import com.pjhn.stepcounter.features.common.repository.IStepGoalRepository
import com.pjhn.stepcounter.features.common.repository.IUserRecordRepository
import com.pjhn.stepcounter.features.common.repository.StepGoalRepository
import com.pjhn.stepcounter.features.common.repository.UserRecordRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindStepGoalRepository(
        stepGoalRepository: StepGoalRepository
    ): IStepGoalRepository

    @Binds
    @Singleton
    abstract fun bindUserRecordRepository(
        userRecordRepository: UserRecordRepository
    ): IUserRecordRepository
}