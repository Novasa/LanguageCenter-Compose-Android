package com.novasa.languagecenter.languagecenterliabary_features.provider

import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.LanguageCenterConfig
import javax.inject.Inject

class LCProviderImpl @Inject constructor(

): LCProvider {

    override val config: LanguageCenterConfig
        get() = _config


    override fun configure(config: LanguageCenterConfig) {
        _config = config
    }
    //fordi jeg ik ved hvordan jeg får adgang til bind
    companion object {
        private lateinit var _config: LanguageCenterConfig
    }
}