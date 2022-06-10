package com.novasa.languagecenter.di

import android.app.Application
import androidx.room.Room
import com.novasa.languagecenter.data.local.DaoDatabase
import com.novasa.languagecenter.data.local.DaoFunctions
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
        .databaseBuilder(application, DaoDatabase::class.java, "LCDatabase")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun providesDao(db: DaoDatabase): DaoFunctions = db.getDao()
}
