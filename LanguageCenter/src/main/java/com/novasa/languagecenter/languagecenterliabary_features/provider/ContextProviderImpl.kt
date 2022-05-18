package com.novasa.languagecenter.languagecenterliabary_features.provider

import android.content.Context
import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.LanguageCenterConfig

class ContextProviderImpl(): ContextProvider  {

    override val context: Context
        get() = _context

    override fun setContext(context: Context) {
        _context = context
    }

    companion object {
        private lateinit var _context: Context
    }
}