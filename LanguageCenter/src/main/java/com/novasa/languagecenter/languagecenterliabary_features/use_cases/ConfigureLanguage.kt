package com.novasa.languagecenter.languagecenterliabary_features.use_cases

import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.LanguageModel
import com.novasa.languagecenter.languagecenterliabary_features.provider.LCProvider
import com.novasa.languagecenter.languagecenterliabary_features.provider.LCProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ConfigureLanguage @Inject constructor(
) {
    private var _currentlanguage = "da"
    private val localLanguage = Locale.getDefault().language

    @Provides
    @Singleton
    fun languageConfiguring(
        languageList: List<LanguageModel>,
        selectedLanguage:  String
    ): String {
        for (language in languageList) {
            _currentlanguage = if (language.codename == localLanguage) {
                localLanguage
            } else if (_currentlanguage != localLanguage && selectedLanguage == language.codename) {
                selectedLanguage
            } else {
                language.codename
            }
        }

        return _currentlanguage
    }
}