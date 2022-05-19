package com.novasa.languagecenter.languagecenterliabary_features.use_cases

import android.util.Log
import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.LanguageModel
import com.novasa.languagecenter.languagecenterliabary_features.provider.LCLanguageProviderImpl
import java.util.*

class ConfigureLanguage {
    private val localLanguage = Locale.getDefault().displayLanguage
    private var selectedLanguage = LCLanguageProviderImpl().language

    fun LanguageConfiguring(
        languageList: List<LanguageModel>
    ) {
        if (selectedLanguage == "") {
            selectedLanguage = "en"
        }
        for (language in languageList) {
            currentLanguage = if (language.codename == localLanguage) {
                localLanguage
            } else if (currentLanguage != localLanguage && selectedLanguage == language.codename) {
                selectedLanguage
            } else {
                language.codename
            }
        }
    }
    companion object {
        var currentLanguage = "da"
    }
}