package com.novasa.languagecenter.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.novasa.languagecenter.data.remote.Api
import com.novasa.languagecenter.data.repostory.Auth
import com.novasa.languagecenter.domain.api_models.LanguageCenterConfig
import com.novasa.languagecenter.provider.LCProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

     private fun http(
         credentials: LanguageCenterConfig,
     ): OkHttpClient.Builder {
         return OkHttpClient.Builder()
             .authenticator( Auth(
                 password = credentials.password,
                 username = credentials.userName,
             )
             )
             .addInterceptor(HttpLoggingInterceptor().apply {
                 level = HttpLoggingInterceptor.Level.BODY
             })
     }

    @Provides
    @Singleton
    fun api(
        provider: LCProvider
    ): Api = Retrofit.Builder()
        .client(http(provider.config).build())
        .baseUrl(provider.config.baseUrl)
        .addConverterFactory(Json{ignoreUnknownKeys = true}.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(Api::class.java)
}
