package com.novasa.languagecenter.languagecenterliabary_features.provider

import android.content.Context
import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.LanguageCenterConfig
import javax.inject.Inject

class LCProviderImpl @Inject constructor(

): LCProvider {
    private lateinit var _config: LanguageCenterConfig

    override val config: LanguageCenterConfig
        get() = _config

    override var language: String
        set(value) {
            language = value
        }
        get() = language

    override fun configure(config: LanguageCenterConfig) {
        _config = config
    }

}