package com.novasa.languagecenter.use_cases

import com.novasa.languagecenter.domain.api_models.LanguageModel
import javax.inject.Inject

class ConfigureLanguage @Inject constructor() {

    fun languageConfiguring(
        languageList: List<LanguageModel>,
        selectedLanguage: String
    ): LanguageModel {

        val languageMap = languageList.associateBy({ it.codename }, { it })

        // Look for selected language in the API list
        return languageMap[selectedLanguage]

            // Language not found, use fallback
            ?: languageList.find { it.is_fallback }

            // No fallback language, use any (should never happen)
            ?: languageList.first()
    }
}
