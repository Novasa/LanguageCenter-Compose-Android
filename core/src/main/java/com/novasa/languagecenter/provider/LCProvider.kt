package com.novasa.languagecenter.provider

import com.novasa.languagecenter.domain.api_models.LanguageCenterConfig

interface LCProvider {

    val config: LanguageCenterConfig

    val language: String


    fun configure (
        config: LanguageCenterConfig
    )

    fun setLanguage(
        language: String
    )
}
