package com.example.languagecenterliabary.languagecenterliabary_features.di

import android.app.Application
import androidx.room.Room
import com.example.languagecenterliabary.languagecenterliabary_features.data.local.DaoDatabase
import com.example.languagecenterliabary.languagecenterliabary_features.data.remote.Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun api(): Api = Retrofit.Builder()
        .baseUrl(Api.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api::class.java)

}