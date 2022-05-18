package com.novasa.languagecenter.languagecenterliabary_features.provider

import android.content.Context
import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.LanguageCenterConfig
import javax.inject.Inject

class LanguageCenterLanguageProviderImpl @Inject constructor(

):LanguageCenterLanguageProvider {
    override val language: String
        get() = _language

    override fun setLanguage(language: String) {
        _language = language
    }

    companion object {
        private var _language = ""
    }
}