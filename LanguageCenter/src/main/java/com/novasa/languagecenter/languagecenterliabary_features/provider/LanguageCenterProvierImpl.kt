package com.novasa.languagecenter.languagecenterliabary_features.provider

import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.LanguageCenterConfig
import javax.inject.Inject

class LanguageCenterProvierImpl @Inject constructor(

): LanguageCenterProvider {
    lateinit var config: LanguageCenterConfig
        private set

    override fun configure(config: LanguageCenterConfig) {
        this.config = config
    }
}