package com.barangbareng.di.module

import android.app.Application
import com.barangbareng.network.ApiRequest
import com.barangbareng.repository.PreferencesRepository
import com.barangbareng.repository.RemoteRepository
import com.barangbareng.repository.RemoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoriesModule {

    @Singleton
    @Provides
    fun provideRemoteRepository(
        apiRequest: ApiRequest
    ): RemoteRepository = RemoteRepositoryImpl(apiRequest)

    @Singleton
    @Provides
    fun providePreferencesRepository(
        application: Application
    ): PreferencesRepository = PreferencesRepository(application.baseContext)
}