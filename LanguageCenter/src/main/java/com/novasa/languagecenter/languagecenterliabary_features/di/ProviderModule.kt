package com.novasa.languagecenter.languagecenterliabary_features.di

import com.novasa.languagecenter.languagecenterliabary_features.provider.LanguageCenterProvider
import com.novasa.languagecenter.languagecenterliabary_features.provider.LanguageCenterProvierImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ProviderModule {
    @Binds
    @Singleton
    fun bindProvider(
        instance: LanguageCenterProvierImpl
    ): LanguageCenterProvider
}