package com.novasa.languagecenter.languagecenterliabary_features.di

import android.util.Log
import com.novasa.languagecenter.languagecenterliabary_features.data.remote.Api
import com.novasa.languagecenter.languagecenterliabary_features.data.repostory.Auth
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.LanguageCenterConfig
import com.novasa.languagecenter.languagecenterliabary_features.provider.LCProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

import okhttp3.OkHttpClient;


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

     private fun http(
         credentials: LanguageCenterConfig,
     ): OkHttpClient.Builder {
         Log.d("credentials", "${credentials.userName} ${credentials.password}")
         return OkHttpClient.Builder()
             .authenticator( Auth(credentials.password, credentials.userName))
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