package com.novasa.languagecenter.languagecenterliabary_features.use_cases

import android.util.Log
import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.LanguageModel
import com.novasa.languagecenter.languagecenterliabary_features.provider.LCProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Singleton


interface ConfigureLanguage {
    val currentLanguage: String
    val selectedLanguage: String
    val currentlanguage: String

    fun languageConfiguring(
        languageList: List<LanguageModel>
    )

}