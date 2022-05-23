package com.novasa.languagecenter.languagecenterliabary_features.di

import android.app.Application
import androidx.room.Room
import com.novasa.languagecenter.languagecenterliabary_features.data.local.DaoFunctions
import com.novasa.languagecenter.languagecenterliabary_features.data.local.LCDatabase
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
    fun providesDatabase(application: Application): LCDatabase = Room
        .databaseBuilder(application, LCDatabase::class.java, "TodoDatabase")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun providesDao(db: LCDatabase): DaoFunctions = db.getDao()
}