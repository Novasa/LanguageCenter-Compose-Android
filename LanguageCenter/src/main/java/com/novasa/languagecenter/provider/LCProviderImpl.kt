package com.novasa.languagecenter.provider

import com.novasa.languagecenter.domain.api_models.LanguageCenterConfig
import javax.inject.Inject

class LCProviderImpl @Inject constructor(): LCProvider {

    private lateinit var _config: LanguageCenterConfig
    private lateinit var _language: String
    private lateinit var _currentLanguage: String

    override val config: LanguageCenterConfig
        get() = _config

    override val language: String
        get() = _language

    override val currentLanguage: String
        get() = _currentLanguage

    override fun configure(config: LanguageCenterConfig) {
        _config = config
    }

    override fun setLanguage(language: String) {
        _language = language
    }

    override fun setCurrentLanguage(language: String) {
        _currentLanguage = language
    }
}
