package com.novasa.languagecenter.di

import com.novasa.languagecenter.provider.LCProvider
import com.novasa.languagecenter.provider.LCProviderImpl
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
        instance: LCProviderImpl
    ): LCProvider
}
