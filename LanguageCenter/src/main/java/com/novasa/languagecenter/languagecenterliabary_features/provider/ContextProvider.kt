package com.novasa.languagecenter.languagecenterliabary_features.provider

import android.content.Context
import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.LanguageCenterConfig

interface ContextProvider {

    val context: Context

    fun setContext (
        config: Context
    )
}