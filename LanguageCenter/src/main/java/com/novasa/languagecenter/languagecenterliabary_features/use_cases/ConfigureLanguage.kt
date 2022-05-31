package com.novasa.languagecenter.languagecenterliabary_features.use_cases

import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.LanguageModel
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
        languageList: Map<String, LanguageModel>,
        selectedLanguage:  String
    ): String {
        for (language in languageList) {
            _currentlanguage = if (language.value.codename == localLanguage) {
                localLanguage
            } else if (_currentlanguage != localLanguage && selectedLanguage == language.value.codename) {
                selectedLanguage
            } else {
                language.value.codename
            }
        }

        return _currentlanguage
    }
}