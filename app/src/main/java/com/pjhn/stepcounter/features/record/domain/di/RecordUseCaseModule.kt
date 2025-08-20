package com.pjhn.stepcounter.features.record.domain.di

import com.pjhn.stepcounter.features.common.repository.IUserRecordRepository
import com.pjhn.stepcounter.features.record.domain.usecase.GetRecordsForPeriodUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RecordUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetRecordsForPeriodUseCase(
        userRecordRepository: IUserRecordRepository
    ): GetRecordsForPeriodUseCase {
        return GetRecordsForPeriodUseCase(userRecordRepository)
    }
}