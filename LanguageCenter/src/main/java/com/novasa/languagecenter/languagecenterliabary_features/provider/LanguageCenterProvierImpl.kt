package com.novasa.languagecenter.languagecenterliabary_features.provider

import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.LanguageCenterConfig
import javax.inject.Inject

class LanguageCenterProvierImpl @Inject constructor(

): LanguageCenterProvider {

    private lateinit var _config: LanguageCenterConfig
    override val config: LanguageCenterConfig
        get() = _config


    override fun configure(config: LanguageCenterConfig) {
        _config = config
    }
}