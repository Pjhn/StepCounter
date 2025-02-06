package com.example.stepcounterapp.features.main.domain.di

import com.example.stepcounterapp.features.main.domain.usecase.GetUserRecordUseCase
import com.example.stepcounterapp.features.main.domain.usecase.IGetUserRecordUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MainUseCaseModule {

    @Singleton
    @Binds
    abstract fun bindGetUserRecordUseCase(getUserRecordUseCase: GetUserRecordUseCase): IGetUserRecordUseCase
}