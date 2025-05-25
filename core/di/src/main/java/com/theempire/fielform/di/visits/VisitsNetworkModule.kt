package com.theempire.fielform.di.visits

import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.WorkerFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.theempire.fielform.core.data.visits.network.NetworkListenerImpl
import com.theempire.fielform.domain.visits.network.NetworkListener
import com.theempire.fielform.domain.visits.sync.UploadManager
import com.theempire.fielform.network.visits.service.VisitApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton
import kotlin.time.Duration.Companion.minutes
import kotlin.time.toJavaDuration

@Module
@InstallIn(SingletonComponent::class)
object VisitsNetworkModule {

    @Provides
    @Named("baseUrl")
    @Singleton
    fun provideBaseUrl() : String = "https://httpbin.org/"

    @Provides
    @Singleton
    fun provideHttpClient() : OkHttpClient =
        OkHttpClient.Builder()
            .callTimeout(1.minutes.toJavaDuration())
            .readTimeout(1.minutes.toJavaDuration())
            .writeTimeout(1.minutes.toJavaDuration())
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .build()

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideRetrofit(
        @Named("baseUrl") baseUrl: String,
        httpClient: OkHttpClient) : Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()

    @Provides
    @Singleton
    fun provideVisitApiService(retrofit: Retrofit): VisitApiService =
        retrofit.create(VisitApiService::class.java)

    @Provides
    @Singleton
    fun provideNetworkListener(
        @ApplicationContext context: Context,
        uploadManager: UploadManager
    ) : NetworkListener = NetworkListenerImpl(context,uploadManager)
}
