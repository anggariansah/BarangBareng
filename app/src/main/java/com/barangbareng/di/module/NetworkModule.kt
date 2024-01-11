package com.barangbareng.di.module

import android.content.Context
import com.barangbareng.network.ApiRequest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun provideApiService(
        @ApplicationContext context: Context
    ): ApiRequest = Retrofit.Builder()
        .baseUrl("http://10.0.2.2/barangbareng_be/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiRequest::class.java)
}