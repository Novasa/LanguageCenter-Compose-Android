package com.novasa.languagecenter.provider

import com.novasa.languagecenter.domain.api_models.LanguageCenterConfig
import java.util.*
import javax.inject.Inject

class LCProviderImpl @Inject constructor() : LCProvider {

    private var manualLanguage: String? = null

    private lateinit var _config: LanguageCenterConfig

    override val config: LanguageCenterConfig
        get() = _config

    override val language: String
        get() = manualLanguage ?: Locale.getDefault().language


    override fun configure(config: LanguageCenterConfig) {
        _config = config
    }

    override fun setLanguage(language: String) {
        manualLanguage = language
    }
}
