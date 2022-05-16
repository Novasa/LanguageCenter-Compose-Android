package com.example.languagecenterliabary.languagecenterliabary_features.di

import android.app.Application
import androidx.room.Room
import com.example.languagecenterliabary.languagecenterliabary_features.data.local.DaoFunctions
import com.plcoding.image_preview.DaoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    @Singleton
    fun providesDatabase(application: Application): DaoDatabase = Room
        .databaseBuilder(application, DaoDatabase::class.java, "TodoDatabase")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun providesDao(db: DaoDatabase): DaoFunctions = db.getDao()
}