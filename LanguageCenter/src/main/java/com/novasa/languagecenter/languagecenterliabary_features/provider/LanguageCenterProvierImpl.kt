package com.novasa.languagecenter.languagecenterliabary_features.provider

import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.LanguageCenterConfig
import javax.inject.Inject

class LanguageCenterProvierImpl @Inject constructor(

): LanguageCenterProvider {

    override val config: LanguageCenterConfig
        get() = _config


    override fun configure(config: LanguageCenterConfig) {
        _config = config
    }

    companion object {
        private lateinit var _config: LanguageCenterConfig
    }
}