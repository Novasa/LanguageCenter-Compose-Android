package com.novasa.languagecenter.languagecenterliabary_features.provider

import android.content.Context
import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.LanguageCenterConfig

interface LCProvider {

    val config: LanguageCenterConfig

    var language: String


    fun configure (
        config: LanguageCenterConfig
    )

}