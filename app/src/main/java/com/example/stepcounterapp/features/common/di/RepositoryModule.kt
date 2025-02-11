package com.example.stepcounterapp.features.common.di

import com.example.stepcounterapp.features.common.repository.IUserRecordRepository
import com.example.stepcounterapp.features.common.repository.UserRecordRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindUserRecordRepository(
        userRecordRepository: UserRecordRepository
    ): IUserRecordRepository
}