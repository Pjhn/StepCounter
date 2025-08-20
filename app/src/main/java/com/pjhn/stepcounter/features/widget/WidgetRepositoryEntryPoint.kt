package com.pjhn.stepcounter.features.widget

import com.pjhn.stepcounter.features.common.repository.IUserRecordRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface WidgetRepositoryEntryPoint {
    fun getUserRecordRepository(): IUserRecordRepository
}