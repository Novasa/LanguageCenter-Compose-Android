package com.novasa.languagecenter.languagecenterliabary_features.provider

import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.LanguageCenterConfig

interface LanguageCenterProvider {
    fun configure (
        config: LanguageCenterConfig
    )
}