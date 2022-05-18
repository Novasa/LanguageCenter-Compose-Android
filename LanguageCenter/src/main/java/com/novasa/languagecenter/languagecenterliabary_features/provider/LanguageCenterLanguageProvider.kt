package com.novasa.languagecenter.languagecenterliabary_features.provider

import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.LanguageCenterConfig

interface LanguageCenterLanguageProvider {
    val language: String

    fun setLanguage (
        language: String
    )
}