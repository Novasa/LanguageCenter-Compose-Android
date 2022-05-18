package com.novasa.languagecenter.languagecenterliabary_features.use_cases

import android.util.Log
import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.LanguageModel
import com.novasa.languagecenter.languagecenterliabary_features.provider.LanguageCenterLanguageProvider
import com.novasa.languagecenter.languagecenterliabary_features.provider.LanguageCenterLanguageProviderImpl
import org.intellij.lang.annotations.Language
import java.util.*

class ConfigureLanguage {
    private val localLanguage = Locale.getDefault().getDisplayLanguage()
    private var selectedLanguage = LanguageCenterLanguageProviderImpl().language

    fun LanguageConfiguring(
        languageList: List<LanguageModel>
    ) {
        if (selectedLanguage == "") {
            selectedLanguage = "en"
        }
        for (language in languageList) {
            if (language.codename == localLanguage) {
                currentLanguage = localLanguage
            } else if (currentLanguage != localLanguage && selectedLanguage == language.codename) {
                currentLanguage = selectedLanguage
            } else {
                currentLanguage = language.codename
            }
        }
        Log.d("theConfiguredLang", "$currentLanguage")
    }
    companion object {
        var currentLanguage = "da"
    }
}