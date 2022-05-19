package com.novasa.languagecenter.languagecenterliabary_features.provider


import javax.inject.Inject

class LCLanguageProviderImpl @Inject constructor(

):LCLanguageProvider {
    override val language: String
        get() = _language

    override fun setLanguage(language: String) {
        _language = language
    }

    //fordi jeg ik ved hvordan jeg får adgang til bind
    companion object {
        private var _language = ""
    }
}