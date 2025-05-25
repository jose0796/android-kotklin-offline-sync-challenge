package com.theempire.fielform.di.visits

import android.content.Context
import com.theempire.fielform.core.data.visits.repository.VisitsRepositoryImpl
import com.theempire.fielform.core.data.visits.sync.UploadManagerImpl
import com.theempire.fielform.core.data.visits.usecases.GetAllVisitsUseCaseImpl
import com.theempire.fielform.core.data.visits.usecases.GetVisitsByStatusUseCaseImpl
import com.theempire.fielform.core.data.visits.usecases.SaveVisitUseCaseImpl
import com.theempire.fielform.domain.visits.repository.VisitRepository
import com.theempire.fielform.domain.visits.sync.UploadManager
import com.theempire.fielform.domain.visits.usecases.GetAllVisitsUseCase
import com.theempire.fielform.domain.visits.usecases.GetVisitsByStatusUseCase
import com.theempire.fielform.domain.visits.usecases.SaveVisitUseCase
import com.theempire.fielform.local.visits.VisitDao
import com.theempire.fielform.network.visits.service.VisitApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class VisitsDataBindingsModule {

    @Binds
    abstract fun bindGetAllVisitsUseCase(getAllVisitsUseCaseImpl: GetAllVisitsUseCaseImpl): GetAllVisitsUseCase

    @Binds
    abstract fun bindGetVisitsByStatusUseCase(getVisitsByStatusUseCaseImpl: GetVisitsByStatusUseCaseImpl): GetVisitsByStatusUseCase

    @Binds
    abstract fun bindSaveVisitUseCase(saveVisitUseCaseImpl: SaveVisitUseCaseImpl): SaveVisitUseCase

}

@Module
@InstallIn(SingletonComponent::class)
object VisitsDataProvidersModule {

    @Provides
    @Singleton
    fun provideUploadManager(@ApplicationContext context: Context) : UploadManager =
        UploadManagerImpl(context)

    @Provides
    @Singleton
    fun providesVisitRepository(
        apiService: VisitApiService,
        visitDao: VisitDao
    ): VisitRepository = VisitsRepositoryImpl(visitDao, apiService)

}
