package com.example.languagecenterliabary.languagecenterliabary_features.di

import com.example.languagecenterliabary.BuildConfig
import com.example.languagecenterliabary.languagecenterliabary_features.data.remote.Api
import com.example.languagecenterliabary.languagecenterliabary_features.data.repostory.Auth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Credentials
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

import okhttp3.OkHttpClient;


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

     private val http = OkHttpClient.Builder()
        .authenticator( Auth("novasa", "kagekage"));

    @Provides
    @Singleton
    fun api(): Api = Retrofit.Builder()
        .client(http.build())
        .baseUrl(Api.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api::class.java)
}