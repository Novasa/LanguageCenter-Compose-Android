package com.novasa.languagecenter.languagecenterliabary_features.use_cases

import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.LanguageModel
import com.novasa.languagecenter.languagecenterliabary_features.provider.LCProvider
import com.novasa.languagecenter.languagecenterliabary_features.provider.LCProviderImpl
import java.util.*
import javax.inject.Inject

class ConfigureLanguageImpl @Inject constructor(
    private val provider: LCProvider
): ConfigureLanguage {

    private var _currentlanguage = "da"

    private var localLanguage = Locale.getDefault().language

    override val currentLanguage: String
        get() = Locale.getDefault().language

    override val selectedLanguage: String
        get() = provider.language

    override val currentlanguage: String
        get() = _currentlanguage

    override fun languageConfiguring(
        languageList: List<LanguageModel>,
        ) {
            for (language in languageList) {
                _currentlanguage = if (language.codename == localLanguage) {
                    localLanguage
                } else if (_currentlanguage != localLanguage && selectedLanguage == language.codename) {
                    selectedLanguage
                } else {
                    language.codename
                }
            }

        }
}