package com.novasa.languagecenter.languagecenterliabary_features.provider

import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.LanguageCenterConfig

interface LanguageCenterProvider {

    val config: LanguageCenterConfig

    fun configure (
        config: LanguageCenterConfig
    )
}