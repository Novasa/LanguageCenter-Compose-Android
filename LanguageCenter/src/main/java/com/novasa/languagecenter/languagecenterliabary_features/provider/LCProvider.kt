package com.novasa.languagecenter.languagecenterliabary_features.provider

import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.LanguageCenterConfig

interface LCProvider {

    val config: LanguageCenterConfig

    fun configure (
        config: LanguageCenterConfig
    )
}