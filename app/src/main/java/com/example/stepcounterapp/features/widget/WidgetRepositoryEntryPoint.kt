package com.example.stepcounterapp.features.widget

import com.example.stepcounterapp.features.common.repository.IUserRecordRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface WidgetRepositoryEntryPoint {
    fun getUserRecordRepository(): IUserRecordRepository
}